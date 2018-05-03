<?php
if(isset($_GET['login']) || isset($_SESSION['steamid'])) {
	require_once "steamauth/userInfo.php";
	require_once "connect.php";
	require_once "steamauth/SteamConfig.php";
	require_once "config.php";

	$conn = @new mysqli($host,$db_user,$db_password,$db_name);

	if($conn->connect_errno!=0) {
		echo"Error:".$conn->connect_errno;
		exit;
	} else {
		if(!file_exists("status.txt")) {
			echo "<div style='position:absolute; top:0; left:0; background:rgba(38, 133, 253,0.9); color:white; width: 100%; z-index: 99999; text-align:center;'>File status not found on server. Report it to admin.</div>";
		}
		$result = $conn->query("SELECT * FROM users WHERE steamid = ".$_SESSION['steamid']) or die ("Query error 1");
		$wiersz = $result->fetch_assoc();
		if(isset($_SESSION['login']) && isset($_SESSION['steamid'])) {
		require "steamauth/SteamConfig.php";
		
		$_SESSION['name'] = $wiersz['name'];
		$_SESSION['avatar'] = $wiersz['avatar'];
		
		$csgo = file_get_contents("http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=".$steamauth['apikey']."&steamid=".$_SESSION['steamid']."&format=json");
		$csgo = json_decode($csgo);
		foreach($csgo->response->games as $data){
			if ($data->appid === 730) {
				$playtime = (int)($data->playtime_forever/60);
				if($playtime>=10) {
					$havecsgo = true;
				} else {
					$havecsgo = false;
				}
				break;
			}
		}

		$steam_group_json = json_decode("http://api.steampowered.com/ISteamUser/GetUserGroupList/v0001/?key=".$steamauth['apikey']."&steamid=".$_SESSION['steamid']."/?xml=1");
		$steam_group_json = json_decode($steam_group_json);

		$steam_group = false;
		if($steam_group_json->response->success == true) {
			foreach($steam_group_json->response->groups as $data) {
				if($data->gid == $steamgroup_id) {
					$steam_group = true;
					break;
				}
			}
		} else {
			echo "<script>console.log('Can not get steam groups. Status false of steampowered api.</script>";
		}
		
		$name = htmlentities($steamprofile['personaname'], ENT_QUOTES, "UTF-8");
		$name = mysqli_real_escape_string($conn, $name);

		if($result->num_rows <= 0 ) {
		$conn->query("INSERT INTO users (steamid,name,avatar,csgo,steam_group) VALUES (".$_SESSION['steamid'].", ". "'". $name. "'".", "."'".$steamprofile['avatarmedium']."'"." ,".(int)$havecsgo." , ".(int)$steam_group.")") or die("Query error 2");
		}
		else {
		$conn->query("UPDATE users SET steamid = '".$_SESSION['steamid']."',name = '".$name."', avatar = '".$steamprofile['avatarmedium']. "' , csgo = ".(int)$havecsgo ." , steam_group = ".(int)$steam_group." WHERE steamid = '".$_SESSION['steamid']."'");
		}
		unset($_SESSION['login']);
		}
		$result->free_result();
		$_SESSION['name'] = $wiersz['name'];
		$_SESSION['coins'] = $wiersz['coins'];
		$conn->close();
	}
}
?>
