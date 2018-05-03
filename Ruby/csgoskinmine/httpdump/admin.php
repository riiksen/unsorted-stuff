HTTP/1.1 302 Found
Server: nginx/1.10.3 (Ubuntu)
Date: Sat, 06 Jan 2018 17:57:45 GMT
Content-Type: text/html; charset=UTF-8
Connection: keep-alive
Expires: Thu, 19 Nov 1981 08:52:00 GMT
Cache-Control: no-store, no-cache, must-revalidate
Pragma: no-cache
Content-Length: 5360

<!DOCTYPE html>
<html lang="pl">
  <head>
    <title>CSGO</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/bootstrap_edit.css">
	<link rel="stylesheet" href="css/bootstrap-table.css">
	<link rel="stylesheet" href="style.css">
	
  </head>
  <body>
  
  
	<div id="bonuscode_modal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Create bonus code</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<span>Code name</span>
						<input id="bonuscode" class="bootbox-input bootbox-input-text form-control" autocomplete="off" type="text">
						<br>
					</div>
					<div class="form-group">
						<span>Code uses</span>
						<input id="uses" class="bootbox-input bootbox-input-text form-control" autocomplete="off" type="text">
						<br>
						<span>Code value IN $</span>
						<input id="value" class="bootbox-input bootbox-input-text form-control" autocomplete="off" type="text">
						<br>
						<button class="btn btn-success" onclick="add_bonus_code();">Create bonus code</button>
					</div>
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
				<img src="img/logo.png" height="45px" style="margin-top:7.5px;"/>
			</a>
			
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".test">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
		</div>
			<div class="collapse navbar-collapse test">
				
				<ul class="nav navbar-nav navbar-right">
				
				<li><a href="?logout">LOGOUT</a></li>
				
				</ul>
			</div>
		</div>
	</nav>
	
	<div id="container">
		<div style="display:flex; flex-wrap:wrap; text-align:center; align-items:center; justify-content:center;">
		<div style="background:green; flex: 1 1 33.333%; padding:50px 0;" onclick="get_user();">GET USER</div>
		<div style="background:yellow; flex: 1 1 33.333%; padding:50px 0;" onclick="get_bonuscodes">GET ALL BONUS CODES</div>
		<div style="background:green; flex: 1 1 33.333%; padding:50px 0; " data-toggle="modal" data-target="#bonuscode_modal">CREATE BONUS CODE</div>
		</div>
		
		<div id="result" style="background:gray; margin-top:25px;">
			<table class="table table-striped" id="table" style="overflow:none;">
		  </table>
		</div>
	</div>
	
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootstrap-table.js"></script>
	<script src="js/colResizable-1.6.js"></script>
	<script src="js/bootstrap-table-resizable.js"></script>
	<script src="js/bootbox.min.js"></script>
	<script src="js/script.js" type="text/javascript"></script>

	<script>
	function get_user() {
		var id = prompt("id");
		if(id!=null) {
			$.ajax({
				url:"",
				data: { 'get_user':id },
				type: "post",
				success: function(data) {
					if(data!="") {
						data = JSON.parse(data);
						
						var column = "<thead><tr>";
						var test = "<tbody><tr>";
						jQuery.each(data,function() {
							column += "<th class='linetext' data-field= '" + this.column + "'>" + this.column + "</th>";
							test += "<td> " +this.value + "</td>";
						});
						column+=" </thead></tr>";
						test+=" </tbody></tr>";
						$("#table").html(column+test);
						
						$('#table').bootstrapTable({
							resizable: true
						});
					}
				}
			});
		}
	}
	function add_bonus_code() {
		if($("#bonuscode").val()!="" && $("#uses").val()!="") {
			bootbox.confirm("Are u sure u want create code: " +$("#bonuscode").val() + ", uses: " + $("#uses").val() + " value: " + $("#value").val(),function(result){ if(result) {
			$.ajax({
				url:"",
				data: {'add_bonuscode':$("#bonuscode").val(),'uses':$("#uses").val(),'value':$("#value").val() },
				type: "post",
				success: function(data) {
					if(data!="") {
						data = JSON.parse(data);
						if(data.success) {
							bootbox.alert({ message:data.message,backdrop:true });
						}
						else {
							bootbox.alert({ message:data.error, backdrop:true });
						}
					}
				}
			});
			}});
		}
	}
	function get_bonuscodes() {
		$.ajax({
			url:"",
			data: {'get_bonuscodes':''},
			type:"post",
			success: function(data) {
				console.log(data);
				if(data!="") {
					data = JSON.parse(data);
					
					if(data.success) {
						bootbox.alert({ message:data.message,backdrop:true });
					}
					else {
						bootbox.alert({ message:data.error, backdrop:true });
					}
				}
			}
		});
	}
	</script>
	
	<style>
	
	.fixed-table-body {
		overflow:hidden;
	}
	
	</style>
  </body>
</html>