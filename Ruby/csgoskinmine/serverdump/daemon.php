<?php
	declare(ticks = 1);
	set_time_limit(0);
	function shutdown() {
		logtofile("Daemon stopped.");
		if (file_exists(dirname(__DIR__)."/csgoskinmine.com/public/status.txt")) {
			unlink(dirname(__DIR__)."/csgoskinmine.com/public/status.txt");
		}
	}
	pcntl_signal(SIGTERM,"shutdown");
	register_shutdown_function("shutdown");
	if(!file_exists("log.txt")) {
		file_put_contents("log.txt","");
	}

	if(!file_exists(dirname(__DIR__)."/csgoskinmine.com/public/status.txt")) {
	file_put_contents(dirname(__DIR__)."/csgoskinmine.com/public/status.txt","");
	}
	require_once dirname(__DIR__)."/csgoskinmine.com/public/config.php";
	require_once dirname(__DIR__)."/csgoskinmine.com/public/connect.php";
		if(!file_exists("log.txt")) {
			file_put_contents("log.txt","");
		}
		
		logtofile("Daemon started.");
		$conn = @new mysqli($host,$db_user,$db_password,$db_name);
		if($conn->connect_errno!=0) {
			
 "Error: ".$conn->connect_errno;
			exit;
		}
		else {
			while(true) {
			$workers = file_get_contents("https://api.nanopool.org/v1/etn/workers/".$crypto_wallet);
			$workers = json_decode($workers);
			if($workers->status = false) {
				logtofile("nanopool api error , status = false");
			}
			else {
				foreach($workers->data as $data) {
					$shares = file_get_contents("https://api.nanopool.org/v1/etn/shareratehistory/".$crypto_wallet."/".$data->id);
					$shares = json_decode($shares,true);
					if($shares['status'] == false) {
						logtofile("Error with nanopool api!");
						exit;
					}
					else {
					$user = $conn->query("SELECT steamid,name,refferal_used,lastshare FROM users where steamid = '".$data->id."'") or die ("Error while getting user from db");

					if($user->num_rows>0) {
						$user_arr = $user->fetch_assoc();
						$lastshare = max($shares['data'])['date'];
						if($lastshare > $user_arr['lastshare']) {
						
						$numberofshares = 0;
							foreach($shares['data'] as $data) {
								if($data['date'] > $user_arr['lastshare']) {
									if($data['shares'] <= 4) {
										$numberofshares += $data['shares']/2;
									}
								}
							}
							$coins = file_get_contents("http://whattomine.com/coins/213.json");
							$coins = json_decode($coins);
							$cryptoprice = file_get_contents("https://api.nanopool.org/v1/etn/prices");
							$cryptoprice = json_decode($cryptoprice);
							if($cryptoprice->status = true) {
							$share = round($sharedifficulty/$coins->difficulty24 * $coins->block_reward24 * $cryptoprice->data->price_usd*100000);
							$totalshare = $numberofshares * round($share*(1.00-$fee-0.02));
							$bonus = 0;
							if(strpos(strtolower($user_arr['name']),"csgoskinmine.com")!==false) {
								$bonus = round($totalshare*0.02);
							}
							$totalshare += $bonus;
							 if($user_arr['refferal_used']!="") {
							$sql = "UPDATE users SET lastshare = ".array_pop($shares->data)->date." , coins = ".$totalshare." WHERE steamid = '".$user_arr['steamid']."';";
							$sql .= "UPDATE users SET refferal_available = refferal_available + ".round($totalshare*$refferal_fee) .", refferal_total = refferal_total + ".round($totalshare*$refferal_fee) ." WHERE steamid = '".$user_arr['refferal_used']."';";
							if($conn->multi_query($sql) === TRUE) {
								lotgofile("Successfly added ".$totalshare." to: ".$user_arr['steamid'].", number of shares: "+ $numberofshares);
							}
							else {
								logtofile("Error while adding coins to: ".$user_arr['steamid']." Shares:".$numberofshares);
							}
							}
							else {
								$conn->query("UPDATE users SET lastshare = ".$lastshare." , coins = coins + ". $totalshare ." WHERE steamid = '". $user_arr['steamid'] ."'") or die("ERROR WITH QUERY WHILE ADDING ". $totalshare ." COINS TO: ". $user_arr['steamid']);
								logtofile("Successfly added ".$totalshare." to: ".$user_arr['steamid'].", number of shares: "+ $numberofshares);
							}
							
							}
							else {
								logtofile("Electroneum (api nanopool) status: false!");
							}
					}
					}
				}
				}
			}
			sleep(120);
			}
		}

function logtofile($msg) {
	$current = file_get_contents("log.txt");
	$current .= "[".date("T")." ".Date("Y:m:d H:i:s")."] ".$msg."\n";
	
	file_put_contents("log.txt",$current.PHP_EOL);
}
?>
