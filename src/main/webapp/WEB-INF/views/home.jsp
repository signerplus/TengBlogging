<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" href="./resources/jquery/css/ui-lightness/jquery-ui-1.8.17.custom.css" rel="stylesheet" />	
<script type="text/javascript" src="./resources/jquery/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="./resources/jquery/js/jquery-ui-1.8.17.custom.min.js"></script>

<style type="text/css" title="currentStyle">
	@import "./resources/DataTables-1.9.0/media/css/demo_page.css";
	@import "./resources/DataTables-1.9.0/media/css/demo_table.css";
</style>
<script type="text/javascript" language="javascript" src="./resources/DataTables-1.9.0/media/js/jquery.dataTables.js"></script>
<script type="text/javascript" language="javascript" src="./resources/jquery/periodicalupdater/jquery.periodicalupdater.js"></script>
<style type="text/css">
	.ui-resizable-se {
		bottom: 17px;
	}
	#amFollowingPanel .ui-selecting { background: #FECA40; }
	#amFollowingPanel .ui-selected { background: #F39814; color: white; }
	#amFollowingPanel { list-style-type: none; margin: 0; padding: 0; width: 60%; }
	#amFollowingPanel li { margin: 3px; padding: 0.4em; font-size: 1.4em; height: 18px; }
</style>
<script type="text/javascript">

$(document).ready(function() {
	
	
	var pathnames = $(location).attr('pathname').split('/');
	var rootPath = pathnames[1]; // blogging
	var url = "/" + rootPath + '/user/isLogin';
	
	$.ajax({
		url: url,
		async: false,
		success: function(data){
			if (data.status == 'false') {
	   			window.location.replace("/" + rootPath);
	   		} else {
	   			$('#loginName').html(data.message);
	   		}
		}
	});

	$("#myBlogPanel").dataTable();
	$("#myTimeStream").dataTable();
	$( '#amFollowingPanel').selectable();
	
	// populate I am following panel amFollowingPanel
	url = "/" + rootPath + '/user/amFollowing';
	$.get(url,
			function(data) {
				$( '#amFollowingPanel').val('');
				for (var i = 0; i < data.length; i++) {
					var item = data[i];
					$( '#amFollowingPanel').append( $('<li/>', {id: item.id, text: item.username, "class": "ui-widget-content" }));
				}					
			}
		);
	
	// populate my blogs panel
	var allBlogsUrl = "/" + rootPath + "/blog/list";
	$.get(allBlogsUrl,
		function(data) {			
			for (var i = 0; i < data.length; i++) {
				var item = data[i];
				$( '#myBlogPanel').dataTable().fnAddData( [
					item.timeStamp,
				    item.content
				] );
			}					
		}
	);
	
	// populate my blog tracking stream
	var allBlogsUrl = "/" + rootPath + "/blog/tracking";
	var ctr = 1;
	$.PeriodicalUpdater(allBlogsUrl,
		{
  			minTimeout: 5*1000,
  			maxTimeout: 10*60*1000,
  			multiplier: 1.000003,
			type: 'json',
			data: function() { return ctr++; },
  			cache:true
		},
		function(data) {
			$( '#myTimeStream').dataTable().fnClearTable();	    		
    		for (var i = 0; i < data.length; i++) {
				var item = data[i];
				$( '#myTimeStream').dataTable().fnAddData( [
					item.timeStamp,
				    item.content,
				    item.blogger
				] );
			}
		}
	);
  	
  	function updateTips( t, tips) {
		tips.text( t )
			.addClass( "ui-state-highlight" );
		setTimeout(function() {
			tips.removeClass( "ui-state-highlight", 1500 );
		}, 500 );
	};
  	
  	$( "#logout" )
	.button()
	.click(function() {
		$.get("/" + rootPath + '/user/logout', function(data){
			window.location.replace("/" + rootPath);		
		});	
	});

  	var tips = $( ".validateTips" );
  	
  	$( "#postIt" )
	.button()
	.click(function() {
		var blogContent = $( "#resizable" ).val();
		if (blogContent.length > 0) {
			var url = "/" + rootPath + '/blog/add';
			$.post(url, 
				{ blogContent: blogContent }, 
				function(data) {
				  	//alert(data.timeStamp + " " + data.content + " " + data.id + " " + data.userId);
				  	updateTips( "Blog is posted successfully", tips);
				  	// update my blog panel
				  	$( '#myBlogPanel').dataTable().fnAddData( [
						data.timeStamp,
						data.content
				  	]);
				  	
				  	$( "#resizable" ).val('');
			});
		} else {
			updateTips( "Please input some content", tips);
		}	
	});
  	
  	$( "#resizable" ).resizable({
  		animate: true
	});
  	
  	function split( val ) {
		return val.split( /,\s*/ );
	}
	function extractLast( term ) {
		return split( term ).pop();
	}
  	
  	var people = [];
  	
  	$( "#peopleSearch" )
	// don't navigate away from the field on tab when selecting an item
	.bind( "keydown", function( event ) {
		if ( event.keyCode === $.ui.keyCode.TAB &&
				$( this ).data( "autocomplete" ).menu.active ) {
			event.preventDefault();
		}
	})
	.bind('focusin', function( event) {
		if (people.length == 0) {
			// look for the current people in the system that are not followed by the user yet
			var url = "/" + rootPath + '/user/listAllUsersNotFollowedYet';
			$.ajax({
				url: url,
				async: false,
				success: function(data){
					for (var i = 0; i < data.length; i++) {
						people.push(data[i].username);
					}
				}
			});
		}
		
	})
	.autocomplete({
		minLength: 0,
		source: function( request, response ) {
			// delegate back to autocomplete, but extract the last term
			response( $.ui.autocomplete.filter(
					people, extractLast( request.term ) ) );
		},
		focus: function() {
			// prevent value inserted on focus
			return false;
		},
		select: function( event, ui ) {
			var terms = split( this.value );
			// remove the current input
			terms.pop();
			// add the selected item
			terms.push( ui.item.value );
			// add placeholder to get the comma-and-space at the end
			terms.push( "" );
			this.value = terms.join( ", " );
			return false;
		}
	});
  	
  	
  	$( "#follow" )
	.button()
	.click(function() {
		var peopleToFollow = $('#peopleSearch').val();
		$.get("/" + rootPath + '/user/follow',
			{people : peopleToFollow },
			function(data){
				var allBlogsUrl = "/" + rootPath + "/blog/tracking";
				$.get(allBlogsUrl,
					function(data) {
						$( '#myTimeStream').dataTable().fnClearTable();
						for (var i = 0; i < data.length; i++) {
							var item = data[i];
							$( '#myTimeStream').dataTable().fnAddData( [
								item.timeStamp,
							    item.content,
							    item.blogger
							] );
						}
						$( "#peopleSearch" ).val('');
						people = [];
						
						var url = "/" + rootPath + '/user/amFollowing';
						$.get(url,
								function(data) {
									$( '#amFollowingPanel :li').val('');
									for (var i = 0; i < data.length; i++) {
										var item = data[i];
										$( '#amFollowingPanel').append( $('<li/>', {id: item.id, text: item.username, "class": "ui-widget-content" }));
									}					
								}
							);
					}
				);		
		});	
	});
});

</script>
</head>

<body>
<table border="1" style="width: 90%">
<tr>
	<td>Welcome <h2 id="loginName"></h2>
		<p><input type='button' id='logout' value='Log Me Out'>
	</td>
	<td colspan="2"><H2>What's Happening?</H2>
<textarea id="resizable" rows="2" cols="40" class="ui-widget-content"></textarea>
<p class="validateTips"></p>
<input type='button' id='postIt' value='Post It'></td>
</tr>
<tr>
	<td>
		<h3>search people</h3>
		<div class="ui-widget">
			<label for="peopleSearch">Search People to follow: </label>
			<input id="peopleSearch" type="text" size="50" />
		</div>
		<input type="button" id='follow' value='Follow' />
		<div>
		<h3>I am following:</h3>
		<ol id="amFollowingPanel">		
		</ol>
		</div>
	</td>
	<td>
		<h3>Time Stream</h3>
		<table id="myTimeStream" class="display">
			<thead>
				<tr><th>Date</th><th>Content</th><th>Blogger</th></tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</td>
	<td>
		<h3>My Blogging Panel</h3>
		<table id="myBlogPanel" class="display">
			<thead>
				<tr><th>Date</th><th>Content</th></tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</td>
</tr>
</table>


</body>
</html>