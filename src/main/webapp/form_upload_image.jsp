
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>The firts form</title>
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet" />
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" />
<script src="<c:url value="/resources/js/jquery-1.12.3.min.js" />"> </script>

</head>
<body>
	<div class="container">
		<h1>Form for upload image.</h1>
		<%=request.getRealPath("/")%>
		<form method="POST" action="<%=request.getContextPath()%>/upload"
			enctype="multipart/form-data">
			<div class="form-group">
				<label class="control-label">Enter file name for upload:</label> <input
					type="text" name="file_name" class="form-control"
					placeholder="File name" id="file-name" />
				<p class="help-block">Enter file name with expansion.</p>
			</div>
			<div class="form-group">
				<label>File input:</label>
				<div class="file_upload">
					<button type="button">Choose file</button>
					<div>File isn't chosen</div>
					<input type="file" name="file">
				</div>
				<p class="help-block">The available format for upload images is "JPG", "GIF", "PNG".</p>
			</div>
			<button type="submit" class="btn btn-default" id="send-button">Upload
				image</button>
		</form>
	</div>
</body>
</html>
<script>
	
	var validFilename = {};
	validFilename['validators'] = ['availableImageExpansion','uniqueImageFileName'];
	
	
	$("#file-name").on('change', function(){
		
		
		
		fileName = $(this).val();
		validFilename['value'] = fileName;
		console.log(validFilename);
		$.ajax({
			url: "<%=getServletContext().getContextPath()%>/validateFileNameField",
									dataType : 'json',
									type : 'POST',
									data : JSON.stringify(validFilename),
									success : success,
								})
					})

	function success(data) {
		console.log(data);
		console.log(data['valid'])
		$formcontrol = $("#file-name").parent();

		if (data['valid'] == false) {
			$formcontrol.addClass("has-error").removeClass("has-success");
			$formcontrol.find("p.help-block").hide();
			$formcontrol.find("span.error-message").remove();
			$("#send-button").attr('disabled', 'disabled');
			for ( var i in data['errorMessages']) {
				$("<span />").addClass('error-message').html(
						data['errorMessages'][i]).appendTo($formcontrol);
			}
		} else {
			$formcontrol.addClass("has-success").removeClass("has-error");
			$formcontrol.find("p.help-block").show();
			$formcontrol.find("span.error-message").remove();
			$("#send-button").removeAttr('disabled');
		}
	}
	
	
	/*style for input type=file*/
	 var wrapper = $( ".file_upload" );
     var inp = wrapper.find( "input" );
     var lbl = wrapper.find( "div" );
     btn = wrapper.find( "button" );
     var file_api = ( window.File && window.FileReader && window.FileList && window.Blob ) ? true : false;
	 
     inp.change(function(){
         var file_name;
         if( file_api && inp[ 0 ].files[ 0 ] )
             file_name = inp[ 0 ].files[ 0 ].name;
         else
             file_name = inp.val().replace( "C:\\fakepath\\", '' );

         if( ! file_name.length )
             return;

         if( lbl.is( ":visible" ) ){
             lbl.text( file_name );
             btn.text( "Choose file" );
         }else
             btn.text( file_name );
     }).change();
</script>