<?php
	require_once "steamauth/steamauth.php";
	require_once "update.php";
	require_once "online_counter.php";
?>
<!DOCTYPE html>
<html lang="pl">
  <head>
    <title>CSGO</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="shortcut icon" href="/favicon.ico">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/bootstrap_edit.css">
	<link rel="stylesheet" href="style.css">
	
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
					<ul class="dropdown-menu animacja">
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
		&nbsp;
		<div id="notification_container">
		</div>
		
		<div id="notification_container">
		</div>
		
		<div class="qa">
			<div class="question linetext">
			How site works?
			</div>
			<div class="answer well">
			You mine cryptocurrency for us, and we give you coins for mined shares!
			</div>
		</div>
		<div class="qa">
			<div class="question linetext">
			How we earn?
			</div>
			<div class="answer well">
			We take small percentage from every share you mine.
			</div>
		</div>
		<div class="qa">
			<div class="question linetext">
			Must I do something before withdraw?
			</div>
			<div class="answer well">
			No, You can withdraw instantly!
			</div>
		</div>
		<div class="qa">
			<div class="question linetext">
			My antivirius found miner as virus, what to do?
			</div>
			<div class="answer well">
			Your antivirus can block our miner, beacuse miner ingerate in components like processor or graphic card. In this case just off your antivirus or add miner to antivirus exceptions.
			</div>
		</div>
		<div class="qa">
			<div class="question linetext">
			Why i earn less than my friend?
			</div>
			<div class="answer well">
			Earnings depends on graphic card.
			</div>
		</div>
		<div class="qa">
			<div class="question linetext">
			I got error "MEMORY ALLOC FAILED VirtualAlloc Failed". What to do?
			</div>
			<div class="answer well">
			Try increase virtaul memory in "Computer Properties / Advanced System Settings / Performance / Advanced / Virtual Memory"
			</div>
		</div>
	</div>
	
	<footer>
	
	<div id="social_media">
	<a target="_blank" href="https://www.facebook.com/csgoskinmine/" class="socialmedia-icon fa fa-facebook-official" style="font-size:40px; margin-right:25px;"></a>
	<a target="_blank" href="https://twitter.com/csgoskinmine" class="socialmedia-icon fa fa-twitter" style="font-size:40px; margin-right:25px;"></a>
	<a target="_blank" href="http://steamcommunity.com/groups/csgoskinminecom" class="socialmedia-icon fa fa-steam-square" style="font-size:40px;"></a>
	</div>
	
	<div id="online">
		<div id="online1"></div><span>0</span>
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
