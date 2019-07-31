app.Classes = {
		validExts: new Array(".class",".jar"),
		tableClass: $('#classTable').DataTable({"language": app.settings.languagePtBr }),
		
		// funcao de inicializacao 
		init: function () {
			
			app.settings.loading.show();
			
			// apaga o storage atual
			app.storage.clear();

			// lista as classes
			app.Classes.list();
	    	
	    	var validExts = new Array(".class",".jar");
	    	$("#uploadFile").attr("accept", app.Classes.validExts);
	    	
	    	$("#uploadFile").on("change", function (event) {
	    		$("#inputGroupFile01").text($(this).val());
	    	});
	    	
	    	$("#btnCancel").on("click", function (event) {
	    		$('#uploadFile').replaceWith($('#uploadFile').val('').clone(true));
	    		$("#inputGroupFile01").text("Selecione uma classe java");
	            $('#classModal').modal('hide');
	    	});
	    	
	    	$("#btnSubmit").click(function (event) {
				event.preventDefault();                
				app.Classes.save();
	        });
		},
		
		// carrega a lista de classes
		list: function(){
			
        	app.Classes.tableClass.rows().remove().draw();
        	
        	$.ajax({
                type: "GET",
                url: $('#classTable').data("url"),
                processData: false,
                contentType: false,
                cache: false,
                timeout: 600000,
                success: function (data) {
                    console.log(data);
                    if (data.success){
                    	var $classList = data.clazzes;
                        
                        // armazena as classes recebidas no storage
                        app.storage.put("classList", $classList);
                    	
	                    $.each($classList, function( i, classe ) {
	                    	app.Classes.tableClass.row.add([
								classe.name, "<a href='#' onclick='app.Classes.listMethods(" + i + ")'>" + classe.qualifiedName + "</a>"]).draw( false );
	                   	});
                    }else{
                    	alertBt({
       	        	      messageText: data.message,
       	        	      headerText: "Alerta",
       	        	      alertType: "danger"
       	        	    });
                    }
        			app.settings.loading.hide();
                },
                error: function (e) {
                	var $msg = $(e.responseText).filter('title').text();
                	if ($msg == '') $msg = "Ocorreu um erro.<br/>";
                    alertBt({
   	        	      messageText: $msg,
   	        	      headerText: "Erro",
   	        	      alertType: "danger"
   	        	    });
        			app.settings.loading.hide();
                }
            });
		},
		
		// carrega os metodos da classe selecionada
		listMethods: function(index){
			
			// recupera a lista de classes do storage
			var $classList = app.storage.get("classList");
    		var obj = new Object();
    		obj.clazz = $classList[index].qualifiedName;
    		
    		$.ajax({
    			url: 'listMethods.op',
    			type: 'POST',
    			dataType: 'json',
    			data: JSON.stringify(obj),
    			contentType: 'application/json',
    			mimeType: 'application/json',
    			success: function (data) {
    				console.log(data);
    				
    				// apaga o storage atual
    				app.storage.clear();
    				
    				// armazena o objeto recebido no storage
    				app.storage.put("methodList", data);
    				
    				// redireciona para a pagina de metodos
    				app.callPage("methods.jsp");
    	        },
    			error:function(data,status,er) {
    				alert("error");
    			}
    		});
		},
		
		// faz o upload do arquivo (classe/jar) selecionado 
		save: function () {

			var $form = $('#fileUploadForm');
            
            if ($('#uploadFile').val() == ""){
            	alertBt({
	        	      messageText: "Selecione uma classe java, arquivo do tipo " + app.Classes.validExts.toString() + ".",
	        	      headerText: "Alerta",
	        	      alertType: "warning"
	        	    });
            	return false;
            }else{
            	var $fileExt = $('#uploadFile').val();
                $fileExt = $fileExt.substring($fileExt.lastIndexOf('.'));
                if (app.Classes.validExts.indexOf($fileExt) < 0) {
					alertBt({
	        	      messageText: "O arquivo selecionado é inválido. Selecione apenas arquivos do tipo " + app.Classes.validExts.toString() + ".",
	        	      headerText: "Alerta",
	        	      alertType: "warning"
	        	    });
					$("#inputGroupFile01").text("Selecione uma classe java");
					$('#uploadFile').replaceWith($('#uploadFile').val('').clone(true));
                  return false;
                }
            }
            
            $("#btnSubmit").attr("disabled", true);
            
            var $data = new FormData($form[0]);
            $.ajax({
                type: "POST",
                enctype: 'multipart/form-data',
                url: $form.attr('action'),
                data: $data,
                processData: false,
                contentType: false,
                cache: false,
                timeout: 600000,
                success: function (data) {
                    console.log(data);
                    $("#btnSubmit").attr("disabled", false);
                    
                    if (data.success){
                    	$('#uploadFile').replaceWith($('#uploadFile').val('').clone(true));
                        $("#inputGroupFile01").text("Selecione uma classe java");
                        $('#classModal').modal('hide');
                        alertBt({
       	        	      messageText: data.message,
       	        	      headerText: "Confirmação",
       	        	      alertType: "success"
       	        	    });
                        app.Classes.list();
                    }else{
                    	alertBt({
         	        	      messageText: data.message,
         	        	      headerText: "Alerta",
         	        	      alertType: "warning"
         	        	    });
                    }                    
                },
                error: function (e) {
                    $("#btnSubmit").attr("disabled", false);
                    $('#uploadFile').replaceWith($('#uploadFile').val('').clone(true));
                    $("#inputGroupFile01").text("Selecione uma classe java");
                    $('#classModal').modal('hide');

                    var $msg = $(e.responseText).filter('title').text();
                	if ($msg == '') $msg = "Ocorreu um erro.<br/><br/><b>Erro</b>: " + e + ".";
                    alertBt({
   	        	      messageText: $msg,
   	        	      headerText: "Erro",
   	        	      alertType: "danger"
   	        	    });
                }
            });
		}
};

$(function() {
    app.Classes.init();
});