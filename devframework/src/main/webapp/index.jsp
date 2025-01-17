<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:_Layout>

    <jsp:attribute name="css_custom">
    </jsp:attribute>

    <jsp:attribute name="content">
        <main role="main">
            <section class="jumbotron text-center">
                <div class="container">
                    <h1 class="jumbotron-heading">List of Classes</h1>
                    <p class="lead">Select the button below to register new java classes.</p>
                    <p>
                        <button type="button" class="btn btn-primary" data-toggle="modal"
                                data-target="#classModal">Add New Java Class</button>
                    </p>
                </div>
            </section>

            <!-- CARREGA A LISTA DE CLASSES COM SERVICOS -->
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="listClass Load">
                            <table id="classTable" class="table table-striped table-bordered" data-url="listClasses.op">
                                <thead>
                                    <tr>
                                        <th style="width:15%">Module</th>
                                        <th style="width:30%">Class</th>
                                        <th style="width:50%">Description</th>
                                        <th style="width:5%" align="center">Delete</th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        &nbsp;<br />&nbsp;<br />
                    </div>
                </div>
            </div>

        </main>

        <!-- Modal -->
        <div class="modal fade" id="classModal" tabindex="-1" role="dialog"
             aria-labelledby="classModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Incluir Nova Classe Java</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <!-- FORM PARA ENVIO DE NOVAS CLASSES/JAR -->
                        <form method="post" action="uploadFile.op" enctype="multipart/form-data" id="fileUploadForm">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="input-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text" id="inputGroupFileAddon01">Upload</span>
                                        </div>
                                        <div class="custom-file">
                                            <input value="" type="file" name="uploadFile"
                                                   class="custom-file-input" id="uploadFile" accept="">
                                            <label class="custom-file-label" for="inputGroupFile01"
                                                   id="inputGroupFile01">Selecione uma classe java</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" id="btnCancel" class="btn btn-secondary">Cancelar</button>
                        <button type="submit" id="btnSubmit" class="btn btn-primary">Upload</button>
                    </div>
                </div>
            </div>
        </div>

    </jsp:attribute>

    <jsp:attribute name="js_custom">
        <script type="text/javascript" charset="iso-8859-1" src="${pageContext.request.contextPath}/resources/js/app/Classes.js"></script>
    </jsp:attribute>
</tags:_Layout>