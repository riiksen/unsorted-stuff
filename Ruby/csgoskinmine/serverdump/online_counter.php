<?php
require_once "connect.php";
if (session_status() == PHP_SESSION_NONE) {
  session_start();
}
if(isset($_POST['online'])) {
	$conn = @new mysqli($host,$db_user,$db_password,$db_name);

	$session    = session_id();
	$time       = time();
	$time_check = $time-10;
	$result = $conn->query("SELECT * FROM online_users WHERE session='$session'");

	if($result && $result->num_rows==0){ 
		$sql1    = "INSERT INTO online_users (session, time) VALUES('$session', '$time')"; 
		$result1 = $conn->query($sql1);
	} else {
		$sql2    = "UPDATE online_users SET time='$time' WHERE session = '$session'"; 
		$result2 = $conn->query($sql2); 
	}
	$result3 = $conn->query("DELETE FROM online_users WHERE time<$time_check");

	$result4 = $conn->query("SELECT * FROM online_users"); 
	echo $result4->num_rows;
	$conn->close();
}
?>
