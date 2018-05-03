<?php
	if (session_status() == PHP_SESSION_NONE) {
    session_start();
	}
	
	if(isset($_POST['setcode']) && isset($_SESSION['steamid'])) {
	require_once "steamauth/userInfo.php";
	require_once "connect.php";
	require_once "steamauth/SteamConfig.php";
	$code = $_POST['setcode'];
	
	$conn = @new mysqli($host,$db_user,$db_password,$db_name);
	
	if($conn->connect_errno!=0) {
		echo"Error:".$conn->connect_errno;
		exit;
	}
	else {
		$checkcsgo = $conn->query("SELECT csgo FROM users WHERE steamid = ".$_SESSION['steamid'])->fetch_assoc();
		if($checkcsgo['csgo'] == 1) {
		$result = $conn->query("SELECT * FROM codes WHERE code = '".$code."'") or die ("Query error 1");
		$wiersz = $result->fetch_assoc();
		$json = "";
		if($result->num_rows <= 0) {
			$codecheck = preg_replace("/[^0-9a-zA-Z\t]/", "", $code);
			
			
			if($codecheck == $code && (strlen($code)>=4 && strlen($code)<=12) ) {
			$checkcode = $conn->query("SELECT * FROM codes WHERE user = ".$_SESSION['steamid']);
			if($checkcode->num_rows>0) {
				$conn->query("UPDATE codes SET code = '".strtolower($code)."' WHERE user = ".$_SESSION['steamid']) or die("query error 11313");
				
				$json = array('success' => true);
			}
			else {
			$conn->query("INSERT INTO codes (code,user) VALUES ('".$code."', '".$_SESSION['steamid']."')") or die ("QUERY ERROR");
			
			$json = array('success' => true);
			}
			}
			elseif($codecheck!=$code){
				$json = array('success' => false, 'error' => "Only alphanumerical characters!"); // alphanumerical
			}
			else {
				$json = array('success' => false, 'error' => "Your code must be between 4 and 12 characters!"); // 4 to 12
			}
		}
		else {
			$json = array('success' => false, 'error' => "This code is used by another account!"); //used code
		}
		
		$result->free_result();
		$conn->close();
		}
		else {
		$json = array('success' => false, 'error' => "You must have csgo played with atleast 10 hours to create code!");
		}
	}
	echo json_encode($json);
	}
	else {
		header("Location: index.php");
	}
?>
