<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="./resources/jquery/css/ui-lightness/jquery-ui-1.8.17.custom.css" rel="stylesheet" />	
<script type="text/javascript" src="./resources/jquery/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="./resources/jquery/js/jquery-ui-1.8.17.custom.min.js"></script>
		
<style>
	label, input { display:block; }
	input.text { margin-bottom:12px; width:95%; padding: .4em; }
	fieldset { padding:0; border:0; margin-top:25px; }
	.ui-dialog .ui-state-error { padding: .3em; }
	.validateTips { border: 1px solid transparent; padding: 0.3em; }
	.loginValidateTips { border: 1px solid transparent; padding: 0.3em; }	
</style>
<title>Demo for a very small MicroBlogging</title>
<script type="text/javascript">
	$(document).ready(function() {
		var pathname = window.location.pathname;
		var url = '';
		
		if (pathname.lastIndexOf('/') === pathname.length -1) {
			url = pathname + 'user/isLogin'; 
		} else {
			url = pathname + '/user/isLogin';
		}
		
		$.get(url,
			function(data) {
				if (data.status == 'true') {					
					window.location.replace(pathname+ "home");					
			}
		});
		
		var register_name = $( "#register_name" ),
			register_password = $( "#register_password" ),
			//allFields = $( [] ).add( name ).add( email ).add( password ),
			allFields = $( [] ).add( register_name ).add( register_password ),
			tips = $( ".validateTips" );

		function updateTips( t, tips) {
			tips.text( t )
				.addClass( "ui-state-highlight" );
			setTimeout(function() {
				tips.removeClass( "ui-state-highlight", 1500 );
			}, 500 );
		}

		function checkLength( o, n, min, max, tips) {
			if ( o.val().length > max || o.val().length < min ) {
				o.addClass( "ui-state-error" );
				updateTips( "Length of " + n + " must be between " +
					min + " and " + max + ".", tips);
				return false;
			} else {
				return true;
			}
		}

		function checkRegexp( o, regexp, n, tips) {
			if ( !( regexp.test( o.val() ) ) ) {
				o.addClass( "ui-state-error" );
				updateTips( n, tips);
				return false;
			} else {
				return true;
			}
		}
		
		$( "#dialog-form" ).dialog({
			autoOpen: false,
			height: 500,
			width: 400,
			modal: true,
			buttons: {
				"Create an account": function() {
					var bValid = true;
					allFields.removeClass( "ui-state-error" );

					bValid = bValid && checkLength( register_name, "username", 3, 16, tips);
					//bValid = bValid && checkLength( email, "email", 6, 80 );
					bValid = bValid && checkLength( register_password, "password", 5, 16, tips);

					bValid = bValid && checkRegexp( register_name, /^[a-z]([0-9a-z_])+$/i, "Username may consist of a-z, 0-9, underscores, begin with a letter.", tips);
					// From jquery.validate.js (by joern), contributed by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
					//bValid = bValid && checkRegexp( email, /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i, "eg. ui@jquery.com" );
					bValid = bValid && checkRegexp( register_password, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9", tips);

					var close = false;
					if ( bValid ) {
						// send request to server
						var pathname = window.location.pathname;
						$.ajax({
  							url: pathname + "user/add",
  							type: 'POST',
  							data: {username: register_name.val(), password: register_password.val()},
  							async: false,
  							success: function(data){
  								if (data.status == 'true') {
  									close = true;
  									updateTips('User created successfully, Please log in again!', tips);  									
  								} else {
  									updateTips('Duplicate User name, Please try again!', tips);
  								}    							
  							}
						});
					}
					
					if (close) {
						window.setTimeout(function() {
							$( "#dialog-form" ).dialog( "close" );
						}, 1000 );						
					}
				},
				Cancel: function() {
					$( this ).dialog( "close" );
				}
			},
			close: function() {
				allFields.val( "" ).removeClass( "ui-state-error" );
			}
		});

		$( "#create-user" )
			.button()
			.click(function() {
				$( "#dialog-form" ).dialog( "open" );
			});
		
		$( "#login-form-reset" )
		.button()
		.click(function() {
			$( "#login-form" )[0].reset();
		});
	
		var login_name = $( "#login_username" ),
			login_password = $( "#login_password" ),			
			login_allFields = $( [] ).add( login_name ).add( login_password ),
			loginTips = $( ".loginValidateTips" );
		
		$( "#login-form-submit" )
		.button()
		.click(function() {
			// validate input and then submit the form
			var bValid = true;
					login_allFields.removeClass( "ui-state-error" );

					bValid = bValid && checkLength( login_name, "username", 3, 16, loginTips);
					//bValid = bValid && checkLength( email, "email", 6, 80 );
					bValid = bValid && checkLength( login_password, "password", 5, 16, loginTips);

					bValid = bValid && checkRegexp( login_name, /^[a-z]([0-9a-z_])+$/i, "Username may consist of a-z, 0-9, underscores, begin with a letter.", loginTips);
					//bValid = bValid && checkRegexp( email, /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i, "eg. ui@jquery.com" );
					bValid = bValid && checkRegexp( login_password, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9", loginTips);

					if ( bValid ) {
						// send request to server
						$.ajax({
  							url: window.location.pathname + "user/checkLogin",
  							type: 'POST',
  							data: {username: login_name.val(), password: login_password.val()},
  							async: false,
  							success: function(data){
  								if (data.status == "true") {
  									var pathname = window.location.pathname;
  									window.location.replace(pathname+ "home");
  								} else {
  									updateTips('Wrong Username and/or password', loginTips);
  								}   							
  							}
						});
					}
		});
	});
	
		
</script>
</head>
<body>
	<h1 align="center">Blogging Demo</h1>
	<div id='effect' style="width:400px">
	<p class="loginValidateTips">All form fields are required.</p>
	<form name="loginForm" id="login-form" action="#" method="post">
		<label>Username</label><input type="text" id="login_username" name="username" class="text ui-widget-content ui-corner-all">
		<label>Password</label><input type="password" id="login_password" name="password" class="text ui-widget-content ui-corner-all">
		<input id='login-form-submit' type="button" value="Login"> <input id="login-form-reset" type="button" value="Cancel">
		<input type="button" value='Create a new user' name='create-user' id='create-user' />		
	</form>
	</div>
	
	<div id="dialog-form" title="Create new user">
	<p class="validateTips">All form fields are required.</p>

	<form>
		<fieldset>
			<label for="name">Username</label>
			<input type="text" name="name" id="register_name" class="text ui-widget-content ui-corner-all" />
		<!-- 		
		<label for="email">Email</label>
		<input type="text" name="email" id="email" value="" class="text ui-widget-content ui-corner-all" />
		 -->
			<label for="password">Password</label>
			<input type="password" name="password" id="register_password" value="" class="text ui-widget-content ui-corner-all" />
		</fieldset>
		</form>
	</div>
</body>
</html>