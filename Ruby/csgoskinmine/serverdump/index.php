<?php
	require_once "steamauth/steamauth.php";
	require_once "update.php";
	require_once "online_counter.php";
	require_once "config.php";
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
				
				<div id="profile_dropdown" class="dropdown" style="display:inline-block;">
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
	&nbsp;
	
	<div id="notification_container">
	</div>
	
		<div id="panel" class="text-center" class="well">
			<hr style="border-color:#d1f3ff"></hr>
			<div id="info" class="clearfix">
				<div class="row-fluid no-gutter" id="info_container">
					<div class="col-12 col-sm-6 col-md-6 col-lg-6 info_column_container">
						<div class="info_column">
						Miner Status
						<div class="info_column_content linetext" id="hs">
						<?php if(isset($_SESSION['steamid'])) { $file = file_get_contents("https://api.nanopool.org/v1/etn/reportedhashrate/".$crypto_wallet."/".$_SESSION['steamid']); $file = json_decode($file); if($file->status) { if($file->data>1) { ?> <span style="color:green;">ON</span> <?php } else { ?> <span style="color:red;">OFF</span> <?php } } else { ?> <span style="color:red;">OFF</span><?php } } else { ?> NOT LOGGED IN <?php } ?>
						</div>
						</div>
					</div>
					
					<div class="col-12 col-sm-6 col-md-6 col-lg-6 info_column_container">
						<div class="info_column">
			 			Nickname bonus
						<div class="info_column_content linetext">
						<?php if(isset($_SESSION['steamid']) && strpos(strtolower($wiersz['name']),$_SERVER['SERVER_NAME']) !== false) { ?> <span style="color:green;">ON <span style="color:#d3b20c;"> (+3%)</span> <span class="glyphicon glyphicon-info-sign nickname_bonus" data-toggle="modal" data-target="#bonus_modal"></span></span> <?php } else { ?> <span style="color:red;">OFF</span> <span class="glyphicon glyphicon-info-sign nickname_bonus" data-toggle="modal" data-target="#bonus_modal"></span> <?php } ?>
						</div>
						</div>
					</div>
				</div>
			</div>
			<hr style="border-color:#d1f3ff"></hr>
			<h2 style="color:black; color:black;">HOW TO START MINING?</h2>
			<?php if(isset($_SESSION['steamid'])) { ?> <button id="download_miner" class="btn btn-primary">Download XMR-STAK <span class="glyphicon glyphicon-download-alt"></span></button> <?php } else { ?> <button onclick="window.location.href='?login'" class="btn btn-primary">Download XMR-STAK <span class="glyphicon glyphicon-download-alt"></span></button> <?php } ?>
			</br>
			</br>
			<div class="alert alert-warning">You will download XMR-STAK miner, configured for your account. Next install drivers needed for mining. Drivers: <a href="https://developer.nvidia.com/cuda-downloads">CUDA DRIVERS</a>, <a href="https://go.microsoft.com/fwlink/?LinkId=746572">https://go.microsoft.com/fwlink/?LinkId=746572</a>. If you want change your account, redownload miner, or change config.txt.
			</br>
			<strong>CHANGE IT IN CONFIG.TXT ON ACCOUNT CHANGE:</strong>
			</div>
			<code style="background:#d8ecf5; color:#3f3ccc;">"wallet_address" : "<?php echo $crypto_wallet; ?>.<strong>YOURSTEAMID64"</strong></code>
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
	<script src="js/FileSaver.js"></script>
	<script src="js/jszip.min.js"></script>
	<script src="js/jszip-utils.js"></script>
	<script src="js/script.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		<?php if(isset($_SESSION['steamid'])) { ?>
		$("#download_miner").on("click",function() {
		var zip = new JSZip();
		zip.file("config.txt", '"pool_list" : [ {"pool_address" : "etn-eu1.nanopool.org:13333", "wallet_address" : "<?php echo $crypto_wallet.".".$_SESSION['steamid']; ?>", "pool_password" : "z", "use_nicehash" : false, "use_tls" : false, "tls_fingerprint" : "", "pool_weight" : 1 }, ], "currency" : "monero", "call_timeout" : 10, "retry_time" : 30, "giveup_limit" : 0, "verbose_level" : 3, "print_motd" : true, "h_print_time" : 60, "aes_override" : null, "use_slow_memory" : "warn", "tls_secure_algo" : false, "daemon_mode" : false, "flush_stdout" : false, "output_file" : "", "httpd_port" : 0, "http_login" : "", "http_pass" : "", "prefer_ipv4" : true,');
		JSZipUtils.getBinaryContent("xmr-stak/libeay32.dll", function (err, data) {
		if(err) {
			throw err; 
		}
		zip.file("libeay32.dll",data,{binary:true});
		JSZipUtils.getBinaryContent("xmr-stak/ssleay32.dll", function (err, data) {
		if(err) {
			throw err; 
		}
		zip.file("ssleay32.dll",data,{binary:true});
		JSZipUtils.getBinaryContent("xmr-stak/libeay32.dll", function (err, data) {
		if(err) {
			throw err; 
		}
		zip.file('xmr-stak.exe', data, {binary:true});
	   	  JSZipUtils.getBinaryContent("xmr-stak/xmr-stak.exe", function (err, data) {
		if(err) {
           throw err;
		}
		zip.file("xmr-stak.exe",data,{binary:true});
		JSZipUtils.getBinaryContent("xmr-stak/xmrstak_opencl_backend.dll", function (err, data) {
		if(err) {
			throw err; 
		}
		zip.file("xmrstak_opencl_backend.dll",data,{binary:true});
		JSZipUtils.getBinaryContent("xmr-stak/xmrstak_cuda_backend.dll", function (err, data) {
		if(err) {
			throw err; 
		}
		zip.file("xmrstak_cuda_backend.dll",data,{binary:true});
		zip.generateAsync({type:"blob"})
		.then(function(content) {
			saveAs(content, "miner_xmrstak.zip");
		});
		});
		});
		});
		});
		});
		});
	});
	
	var timer;
	timer = setInterval(function(){
			var time = new Date("<?php echo $wiersz['daily_bonus'];?>");
			var now = new Date();
			now.setTime(now.getTime()+now.getTimezoneOffset()*60*1000);
			var offset = 60;
			var nowCET = new Date(now.getTime() + offset*60*1000);
			if(now-time<0) {
					var time = Math.floor((time-nowCET)/1000);
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
