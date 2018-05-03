<?php

require_once "steamauth/steamauth.php";
require_once "connect.php";

if (session_status() == PHP_SESSION_NONE) {
	session_start();
}
if(!isset($_SESSION['steamid'])) {
	echo"<script> document.write(".loginbutton()."); </script>";
	exit;
}
$conn = @new mysqli($host,$db_user,$db_password,$db_name);
if($conn->connect_errno!=0) {
	echo"Error:".$conn->connect_errno;
	exit;
}
else {
	$user = $conn->query("SELECT refferal_count FROM users WHERE steamid = ".$_SESSION['steamid']) or die ("Error while getting refferal count 1!");
	$user_arr = $user->fetch_assoc();
	$result = $conn->query("SELECT id FROM users WHERE refferal_used = ".$_SESSION['steamid']) or die ("Error while getting refferal count 2!");
	if($result->num_rows!=$user_arr['refferal_count']) {
		$conn->query("UPDATE users SET refferal_count = ".$result->num_rows." WHERE steamid = ".$_SESSION['steamid']) or die ("Error with query while updating refferal count!");
	}
	$user->free_result();
	$result->free_result();
	$conn->close();
}
require_once "update.php";

?>
<!DOCTYPE html>
<html lang="pl">
<head>
    <title>CSGO</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="shortcut icon" href="/favicon.ico">
	<link rel="stylesheet" href="style.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/bootstrap_edit.css">
</head>

<body>

	
	<div id="refferal_modal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Reedem code</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<span>Promo code</span>
						<input id="promocode" class="bootbox-input bootbox-input-text form-control" autocomplete="off" type="text">
						<br>
						<button class="btn btn-success" onclick="promo_code();">Redeem promo code</button>
					</div>
					<div class="form-group">
						<span>Bonus code</span>
						<input id="bonuscode" class="bootbox-input bootbox-input-text form-control" autocomplete="off" type="text">
						<br>
						<button class="btn btn-success" onclick="bonus_code();">Redeem bonus code</button>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<div id="bonus_modal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">BONUS!</h4>
				</div>
				<div class="modal-body">
				<div class="text-center well">
				Bonuses:
				<br>
				- 2000 COINS EVERYDAY
				<br>
				(3000 if You join to steam group: <a href="http://steamcommunity.com/groups/csgoskinminecom">click</a>)
				<br>
				After joining to steam group relogin to receive bonus!
				<br>
				- 2% MORE EARNINGS FROM MINING!
				<br>
				</div>
				
				<button class="btn btn-success btn-block" style="width:50%; margin:0 auto;" id="timer" onclick="daily_bonus();"><?php if(isset($_SESSION['steamid'])) { $dailybonus = new DateTime($wiersz['daily_bonus']); if((new DateTime())>=$dailybonus) { echo "CLAIM!"; } } else { echo "00:00:00"; }?></button>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<nav class="navbar navbar-default navbar-static-top">
		<div class="container">
		
		<div class="navbar-header">
			<a href="index.php" class="navbar-brand">
				<img src="img/logo.png" height="45px" style="margin-top:7.5px;"></img>
			</a>
			
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".test">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
		</div>
			<div class="collapse navbar-collapse test">
				<ul class="nav navbar-nav navbar-left">
					<li><a href="index">Earn</a></li>
					<li><a href="withdraw">Withdraw</a></li>
					<li><a href="faq">Faq</a></li>
					<li><a href="#" onclick="bootbox.alert({ message:'Contact e-mail: csgoskinmine@gmail.com',backdrop:true });">Contact</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
			<?php
			if(isset($_SESSION['steamid'])) {
			?>	
				
				<div id="profile_dropdown" class="dropdown">
				<div id="profile" class="dropdown-toggle" type="button" data-toggle="dropdown">
					<img style="border-radius:50%; user-select:none;" src="<?php echo($_SESSION['steam_avatarmedium']); ?>" width="45" height="45"/>
					<div id="balance" class="linetext">Balance: <?php echo $_SESSION['coins'];?></div>
					<span class="caret"></span>
				</div>
					<ul class="dropdown-menu animation">
						<li><a href="refferals.php">Refferals</a></li>
						<li><a href="#" data-toggle="modal" data-target="#refferal_modal">Redeem code</a></li>
						<li><a href="#" data-toggle="modal" data-target="#bonus_modal">BONUS</a></li>
						<li class="divider"></li>
						<li><a href="?logout">Logout</a></li>
					</ul>
				</div>
				
			<?php } else { ?>
					<a href="?login" style="display:block;"><img src="https://steamcommunity-a.akamaihd.net/public/images/signinthroughsteam/sits_01.png"/></a>
			<?php } ?>
				</ul>
			</div>
		</div>
	</nav>
	
	<div id="container">
		<div id="notification_container">
		</div>

		&nbsp;
		<div id="panel" class="well">
			<div class="row">
				<div class="col-xs-12 alert alert-success text-center">Your refferal code: <?php if(empty($code['code'])) {?><a href="#" class="link_underline" style="color: #1f71f4;" onclick="setcode_modal();">SET CODE</a> <?php } else { echo "<b>".$code['code']."</b>" ?><a href="#" class="link_underline" style="color: #1f71f4; padding-left:5px;" onclick="setcode_modal();">change code</a><?php } ?> </div>
			</div>
		<table class="table table-condensed" style="background:silver; border-radius:10px; table-layout:fixed; ">
			<tbody>
		    	<tr>
		    		<td style="border:0;">Refferals count:</td>
		    		<td style="border:0;"><?php if(isset($_SESSION['steamid'])) { echo $wiersz['refferal_count']; } else { ?> NOT LOGGED IN	<?php } ?></td>
		    	</tr>
				<tr>
					<td>Lifetime Earnings:</td>
					<td><?php if(isset($_SESSION['steamid'])) { echo $wiersz['refferal_total']; } else { ?> NOT LOGGED IN	<?php } ?></td>
				</tr>
				<tr>
					<td><abbr title="You earn 2% of refferals mined coins + 2 cents for every new refferal!">Avaliable Now</abbr></td>
					<td><?php if(isset($_SESSION['steamid'])) { echo $wiersz['refferal_available']; } else { ?> NOT LOGGED IN	<?php } ?></td>
				</tr>
			</tbody>
		</table>
		
		<button class="btn btn-success btn-block" style="width:500px; margin:0 auto;" id ="collect_earnings" onclick="collect_earnings();"><span></span>Collect earnings</button>
		</div>
	</div>
	
	<footer style="background:rgb(250, 251, 208); position:absolute; bottom:-50px; height:50px; width:100%;" class="text-center">
	
	<div id="social_media">
	<a target="_blank" href="https://www.facebook.com/csgoskinmine/" class="socialmedia-icon fa fa-facebook-official" style="font-size:40px; margin-right:25px;"></a>
	<a target="_blank" href="https://twitter.com/csgoskinmine" class="socialmedia-icon fa fa-twitter" style="font-size:40px; margin-right:25px;"></a>
	<a target="_blank" href="http://steamcommunity.com/groups/csgoskinminecom" class="socialmedia-icon fa fa-steam-square" style="font-size:40px;"></a>
	</div>
	
	</footer>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootbox.min.js"></script>
	<script src="js/script.js" type="text/javascript"></script>
	
	<script type="text/javascript">
	var timer;
	<?php if(isset($_SESSION['steamid'])) { ?>
	timer = setInterval(function(){
			var czas = new Date("<?php echo $wiersz['daily_bonus'];?>");
			var now = new Date();
			now.setTime(now.getTime()+now.getTimezoneOffset()*60*1000);
			var offset = 60;
			var nowCET = new Date(now.getTime() + offset*60*1000);
			if(now-czas<0) {
					var time = Math.floor((czas-nowCET)/1000);
				    var h = Math.floor(time / 3600);
					var m = Math.floor(time % 3600 / 60);
					var s = Math.floor(time % 3600 % 60);
					$("#timer").html(h +":"+m+":"+s);
					$("#timer").addClass("disabled");
					$("#timer").attr("disabled", true);
			}
			else {
				$("#timer").removeClass("disabled");
				clearInterval(timer);
				$("#timer").html("Claim!");
				$("#timer").attr("disabled", false);
			}
	},750);
	<?php } ?>
	</script>
	
</body>

</html>
