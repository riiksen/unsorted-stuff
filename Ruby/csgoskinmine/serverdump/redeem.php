<?php
require_once "connect.php";
require_once "update.php";
require_once "config.php";
if (session_status() == PHP_SESSION_NONE) {
	session_start();
}
if((isset($_POST['promocode']) || isset($_POST['bonuscode']) || isset($_POST['refferal']) || isset($_POST['dailybonus']) || isset($_POST['update_balance']) ) &&  isset($_SESSION['steamid'])) {
	$conn = @new mysqli($host,$db_user,$db_password,$db_name);
	if($conn->connect_errno!=0) {
		echo"Error:".$conn->connect_errno;
		exit;
	} else {
		$csgocheck = $conn->query("SELECT csgo FROM users WHERE steamid = ".$_SESSION['steamid']) or die("QUERY ERROR");
		$csgocheck = $csgocheck->fetch_assoc();
		if(isset($_POST['promocode'])) {
			$user = $conn->query("SELECT * FROM users WHERE steamid = '".$_SESSION['steamid']."'");
			$user_arr = $user->fetch_assoc();
			if($user_arr['refferal_used']=="") {
				if($csgocheck['csgo'] == 1 ) {
					$codeowner = $conn->query("SELECT * FROM codes WHERE code = '".$_POST['promocode']."'"); //Code owner NOTE: A sql injection vuln
					$code_owner = $codeowner->fetch_assoc();
					if($codeowner->num_rows >0) {
						$user_code = $conn->query("SELECT * FROM codes WHERE user = ".$_SESSION['steamid']) or die("QUERY ERROR");
						$user_code = $user_code->fetch_assoc();
						if($user_code['code']!=$code_owner['code']) {
							if($conn->query("UPDATE users SET refferal_used = '".$code_owner['user']."' , coins = " . (int)($user_arr['coins']+$refferal_code_value) ." WHERE steamid = ".$user_arr['steamid'])) { //NOTE: Sql injection vuln
								$json = array('success' => true, 'message' => "Success!".$refferal_code_value." Coins have been added to your account!");
								$user->free_result();
							} else {
								$json = array('success' => false, 'error' => "Error with query!");
							}
						} else {
							$json = array('success' => false, 'error' => "You can't use your own code!");
						}
					} else {
						$json = array('success' => false, 'error' => "This code doesn't exists!");
					}
				} else {
					$json = array('success' => false, 'error' => "You must have cs:go with played at least 10 hours to redeem code!");
				}
			} else {
				$json = array('success' => false, 'error' => "You can use promocode only once!");
			}
			echo json_encode($json);
		}
		if(isset($_POST['bonuscode'])) {
			$bonuscode = $_POST['bonuscode'];
			$json = "";
			if($csgocheck['csgo'] == 1 ) {
				$code1 = $conn->query("SELECT code,uses,value FROM bonus_codes WHERE code = '".$bonuscode."'") or die ("ERROR WITH QUERY WHILE REDEEMING BONUS CODE!"); //NOTE: SQL injection vuln
				$code1_arr = $code1->fetch_assoc();
				if($code1->num_rows>0) {
					if($code1_arr['uses']>0) {
						if($code2 = $conn->query("SELECT * FROM code_".$bonuscode." WHERE steamid = ".$_SESSION['steamid'])) { //NOTE: SQLi vuln
							if($code2->num_rows<=0) {
								$sql = "UPDATE bonus_codes SET uses = uses - 1 WHERE code = '".$bonuscode."';"; //NOTE: SQLi vuln
								$sql .= "INSERT INTO code_".$bonuscode." (steamid) VALUES (".$_SESSION['steamid'].");";
								$sql .= "UPDATE users SET coins = coins + ".$code1_arr['value']." WHERE steamid = '".$_SESSION['steamid']."' ;";
								if($code1_arr['uses']==1) {
									$sql .= "DELETE FROM bonus_codes WHERE code = '".$bonuscode."';"; //NOTE: SQLi vuln
									$sql .= "DROP TABLE code_".$bonuscode.";";
								}
								if($conn->multi_query($sql) === TRUE) {
									$json = array('success' => true, 'message' => "Success! ".$code1_arr['value']." coins have been added to your account! ".$code1_arr['uses']);
								} else {
									$json = array('success' => false, 'error' => "ERROR WITH QUERY WHILE REDEEMING BONUS CODE 2!");
								}
							}
						} else {
							die(json_encode(array('success' => false, 'error' => "Error with code! Please report it to admin!")));
						}
					} else {
						$sql = "DELETE FROM bonus_codes WHERE code = '".$bonuscode."';"; //NOTE: SQLi vuln
						$sql .= "DROP TABLE code_".$bonuscode.";";
						if($conn->multi_query($sql) === TRUE) {
						} else {
							die("<script>bootbox.alert({ message:'Error with query when dropping used code table. Please report it to admin!' });</script>");
						}
						$json = array('success' => false, 'error' => "This bonuscode doesn't exists!");
					}
				} else {
					$json = array('success' => false, 'error' => "This bonuscode doesn't exists!");
				}
			} else {
				$json = array('success' => false, 'error' => "You must have cs:go with atleast 10 hours played to redeem bonus code!");
			}
			$code1->free_result();
			$code2->free_result();
			echo json_encode($json);
		}
		if(isset($_POST['refferal'])) {
			if($csgocheck['csgo'] == 1 ) {
				$user = $conn->query("SELECT refferal_available, name FROM users WHERE steamid = ".$_SESSION['steamid']) or die ("Error with query. Refferal collect.");
				$user_arr = $user->fetch_assoc();
				if($user_arr['refferal_available']>=50000) {
					$conn->query("UPDATE users SET refferal_available = 0, coins = coins + ".$user_arr['refferal_available']." WHERE steamid = ".$_SESSION['steamid']) or die("Query error when collecting refferal bonus!");
					$json = array('success' => true, 'message' => "Success! You've recived ".$user_arr['refferal_available']." coins!");
				} else {
					$json = array('success' => false, 'error' => "You must have atleast 50000 coins to collect!");
				}
			} else {
				$json = array('success' => false, 'error' => "You must have cs:go!");
			}
			$user->free_result();
			echo json_encode($json);
		}
		if(isset($_POST['dailybonus'])) {
			$user = $conn->query("SELECT steam_group,daily_bonus, name FROM users WHERE steamid = ".$_SESSION['steamid']) or die("Error with query. Daily bonus claim.");
			$user_arr = $user->fetch_assoc();
			if($csgocheck['csgo'] == 1 && strpos(strtolower($user_arr['name']),$_SERVER['SERVER_NAME'])!==false) {
				$dailybonus_time = new DateTime($user_arr['daily_bonus']);
				$json = "";
				if(new DateTime()>=$dailybonus_time) {
					if($user_arr['steam_group'] == 1) {
						$dailybonus = 3000;
					}
					if($conn->query("UPDATE users SET coins = coins + ".$dailybonus.", daily_bonus = now() + INTERVAL 1 DAY WHERE steamid = ".$_SESSION['steamid'])) {
						$json = array('success' => true, 'message' => "Success! You've recived ".$dailybonus);
					} else {
						die("Error with query. Daily bonus claim 2.");
					}
				}
			} elseif($csgocheck['csgo'] == 0) {
				$json = array('success' => false, 'error' => "You must have cs:go!");
			} else {
				$json = array('success' => false, 'error' => "You must have ".$_SERVER['SERVER_NAME']." in nickname to receive bonus!");
			}
			echo json_encode($json);
		}
	}
} else {
header("Location:index.php");
}
?>
