<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:_Layout>

	<jsp:attribute name="css_custom">
	</jsp:attribute>
	
	<jsp:attribute name="content">
		<main role="main">
			
			<nav aria-label="breadcrumb">
				<ol class="breadcrumb">
			    	<li class="breadcrumb-item">
			    		<a href="./">Home</a>
			    		<i class="fas fa-angle-right mx-2" aria-hidden="true"></i>
			    		
			    		
			    	</li>
			    	<li class="breadcrumb-item active" aria-current="page">Documentação</li>
				</ol>
			</nav>

			<section class="jumbotron text-center">
				<div class="container">
					<h1 class="jumbotron-heading"><strong>Documentação</strong> </h1>
				</div>
			</section>
	
			<div class="container">
				<div class="row">
					<div class="col-md-12">
					<h3>Utilizando a ferramenta</h3>
                  	<p>No momento que for utilizar a ferramenta pela primeira vês ela estará assim:<br>
                      <img src="resources/images/virtuallab.png" alt="virtualLabGui" width=1024></p>
                 <p> Em seguida vai em adicionar nova classe e adicione as classes ou jars que necessita para utilizar a ferramenta.<br>
                      <img src="resources/images/virtuallab_envio.png" alt="virtualLabGui" width=1024></p>
                  <p>Este por sua vês vai abrir essa janela.</p>
                  <p>Selecione Browse e selecione o arquivo.</p>
                  <img src="resources/images/virtuallab_envio_incluir.png" alt="virtualLabGui" width=1024 ><br>
                  <img src="resources/images/virtuallab_envio_fileUpload.png" alt="virtualLabGui" width=1024><br>
                  <img src="resources/images/virtuallab_envio_fileUpload.png" alt="virtualLabGui" width=1024><br>
                  <img src="resources/images/virtuallab_envio_upload.png" alt="virtualLabGui" width=1024><br>
				  <img src="resources/images/virtuallab_selectMethod.png" alt="virtualLabGui" width=1024><br>
				  <img src="resources/images/virtuallab_executar.png" alt="virtualLabGui" width=1024><br>
				  <img src="resources/images/virtuallab_resultado.png" alt="virtualLabGui" width=1024><br>
				  
				</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<h3>Desenvolvimento utilizando a ferramenta</h3>
						<h4>Conexão ao banco de dados</h4>
						<p>Cria a classe entidade</p>
						<pre>
							<code>
@Entity
public class Temperatura
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
							
	private String latitude;
	private String longitude;
	private String local;
	private double maxima;
	private double minima;
	private String mes;
									
	public Temperatura()
	{		
	}
									
	public Temperatura(Long id, String lat, String lng, String local, double max, double min, String mes)
	{
		this.id = id;
		this.latitude = lat;
		this.longitude = lng;
		this.local = local;
		this.maxima = max;
		this.minima = min;
		this.mes = mes;
	}
	//Gets e sets omitidos
								
}
																
							
							</code>
						</pre>
					</div>
				</div>
			</div>
	
		</main>

	</jsp:attribute>
	
	<jsp:attribute name="js_custom">
		
	</jsp:attribute>
</tags:_Layout>