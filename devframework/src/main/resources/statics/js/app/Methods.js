app.Methods = {
		methodClass: $('#methodTable').DataTable(
				{
					"language": app.settings.languagePtBr,
					"paging": false,
					"searching": false,
					"ordering": false,
			        "info":     false
				}),
				
		// funcao de inicializacao 
		init: function () {
			
			// atualiza os elementos da pagina com o nome da classe
        	var classDesc = app.storage.get("classDescriptor");        	
			$('#breadcrumbClassName').text(classDesc.qualifiedName);
			$('#headerClassName').text(classDesc.label);
			
			app.settings.loading.show();
			
			// lista os metodos
			app.Methods.list();
		},
		
		// carrega a lista de metodos
		list: function(){
			
        	app.Methods.methodClass.rows().remove().draw();
        	
			// recupera a lista de metodos do storage
        	var methodList = app.storage.get("methodList").methods;
            $.each(methodList, function (i, method) {
           	 
            	app.Methods.methodClass.row.add([
            		method.label,
					"<a href='#' onclick='app.Methods.invokeMethod(" + i + ")'>" + app.utils.methodSignature(method) + "</a>",
					method.description]).draw( false );
           	});
            app.settings.loading.hide();
		},
		
		//  metodo selecionado para ser invocado
		invokeMethod: function(index){
			
			// recupera o descritor do metodo escolhido
    		var methodDesc = app.storage.get("methodList").methods[index];
			
			// armazena  recebido no storage
			app.storage.put("methodDescriptor", methodDesc);
			
			// redireciona para a pagina de invocao de metodos
			app.callPage("invokeMethod.jsp");
		}
};

$(function() {
    app.Methods.init();
});