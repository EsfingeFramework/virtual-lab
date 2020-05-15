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
 			    	<li class="breadcrumb-item active" aria-current="page">Documentation</li> 				</ol>
			</nav>

			<section class="jumbotron text-center">
				<div class="container">
 					<h1 class="jumbotron-heading"><strong>Documentation</strong> </h1> 				</div>
			</section>
	
			<div class="container">
				<div class="row">
					<div class="col-md-12">
					<h3>Utilizando a ferramenta</h3>
                  	<p>No momento que for utilizar a ferramenta pela primeira v�s ela estar� assim:<br>
                      <img src="resources/images/virtuallab.png" alt="virtualLabGui" width=1024></p>
                 <p> Em seguida vai em adicionar nova classe e adicione as classes ou jars que necessita para utilizar a ferramenta.<br>
                      <img src="resources/images/virtuallab_envio.png" alt="virtualLabGui" width=1024></p>
                  <p>Este por sua v�s vai abrir essa janela.</p>
                  <p>Selecione Browse e selecione o arquivo.</p>
                  <img src="resources/images/virtuallab_envio_incluir.png" alt="virtualLabGui" width=1024 ><br>
                  <p>Por enquanto o tipo de arquivo que pode ser selecionado � java</p>
                  <p>Clique em abrir para abrir</p>
                  <img src="resources/images/virtuallab_envio_main.png" alt="virtualLabGui" width=1024><br>
                  <p>Selecione a classe que vai ser aberta</p>
                  <img src="resources/images/virtuallab_envio_upload.png" alt="virtualLabGui" width=1024><br>
				  <p>Selecione o m�todo que vai ser executado</p>
				  <img src="resources/images/virtuallab_selectMethod.png" alt="virtualLabGui" width=1024><br>
				  <p>Se n�o tiver nenhum paramentro clique em executar, caso contrario, preencher com os par�metros.</p>
				  <img src="resources/images/virtuallab_executar.png" alt="virtualLabGui" width=1024><br>
				  <p>Vai ser mostrado o resultado a seguir.</p>
				  <img src="resources/images/virtuallab_resultado.png" alt="virtualLabGui" width=1024><br>
				  
				</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<h3>Desenvolvimento utilizando a ferramenta</h3>
						<h4>Conex�o ao banco de dados utilizando <code>@ServiceDAO</code></h4>
						<p>Cria a classe entidade padr�o do JPA</p>
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
						
<p>Coloca a anota��o <code>@ServiceDAO</code> ela contem os parametros <code>label</code> que � uma label,<code>description</code> que � a descri��o da classe,
<code>url</code> a url do jbdc nesse exemplo url = "jdbc:postgresql://localhost:5432/postgres",
 <code>user</code> usuario do banco de dados,
  <code>password</code> password do banco de dados, <code>dialect</code> qual � o banco de dados que est� sendo utilizado.
</p>						
						<pre>
							<code>

@ServiceDAO(
	label = "DAO",
	description = "Demonstra��o da anota��o @ServiceDAO.",
	url = "jdbc:postgresql://localhost:5432/postgres", 
	user = "postgres", 
	password = "postgres", 
	dialect = "org.hibernate.dialect.PostgreSQLDialect")
public interface DaoDemo extends Repository&lt;Temperatura&gt;
{
	
	@ServiceMethod(
		label = "Listar todas temperaturas",
		description = "Retorna todos as temperaturas cadastradas no Banco de Dados.")
	@TableReturn
	public List&lt;Temperatura&gt; getTemperatura();
	
	
	@ServiceMethod(
		label = "Listar temperaturas por m�s",
		description = "Retorna as temperaturas referentes ao m�s informado.")
	@TableReturn
	public List&lt;Temperatura&gt;getTemperaturaByMes(String mes);

	
	@ServiceMethod(
		label = "Listar temperaturas m�ximas",
		description = "Retorna as temperaturas com m�xima igual ou maior ao valor informado.")
	@TableReturn
	public List&lt;Temperatura&gt; getTemperaturaByMaximaOrderByMaximaAsc(@GreaterOrEquals double temp);

	
	@ServiceMethod(
			label = "Listar temperaturas m�nimas",
			description = "Retorna as temperaturas com m�nima igual ou menor ao valor informado.")
	@TableReturn
	public List&lt;Temperatura&gt; getTemperaturaByMinimaOrderByMinimaDesc(@LesserOrEquals double temp);
}		
							</code>
						</pre>

<h4>Desenvolvimento utilizando o <code>@ServiceClass</code></h4>
<p></p>
						<pre>
							<code>
@ServiceClass(
	label = "GR�FICOS - BARRAS",
	description = "Demonstra��o da anota��o @BarChartReturn.")
public class BarChartDemo
{
	
		@ServiceMethod(
		label = "Criar gr�fico - Lista",
		description = "@BarChartReturn sem par�metros.")
	@BarChartReturn
	public List&lt;Number&gt; createChartByList()
	{
		List&lt;Number&gt; list = new ArrayList<>();
		list.add(12);
		list.add(19);
		list.add(3);
		list.add(7);
		list.add(10);
		
		return list;
	}

}	

							</code>
						</pre>
<h4>Lista de anota��es</h4>
<p>Segue aqui uma tabela com a lista das anota��es</p>
<table class="table">
	<caption>Lista de anota��es do VirtualLab</caption>
	<thead>
	<tr>
		<th>Anota��o</th>
		<th>Uso</th>
		<th>Descri��o<br></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td>@ServiceDAO</td>
		<td>Usado em Interfaces</td>
		<td>Usado para definir uma conex��o com o banco de dados utilizando o Esfinge QueryBuilder</td>
	</tr>
	<tr>
		<td>@ServiceClass</td>
		<td>Usado em Classe</td>
		<td>Usado para definir se a classe � visivel para ser utilizado com a aplica��o</td>
	</tr>
	<tr>
		<td>@ServiceMethod</td>
		<td>Usado em Metodos</td>
		<td>Utilizado para definir os m�todos visiveis a aplica��o</td>
	</tr>
	<tr>
		<td>@TableReturn</td>
		<td>Usado em Metodos</td>
		<td>Junto com o @ServiceMethod � utilizado para a visualiza��o em forma de tabela</td>
	</tr>
	
	<tr>
		<td>@BarChartReturn</td>
		<td>Usado em Metodos</td>
		<td>Retorna um grafico de barras</td>
	</tr>
	
	<tr>
		<td>@LineChartReturn</td>
		<td>Usado em Metodos</td>
		<td>Retorna um grafico de linhas</td>
	</tr>
	
	<tr>
		<td>@CustonReturn</td>
		<td>Usado em Metodos</td>
		<td>&nbsp;</td>
	</tr>
	
	<tr>
		<td>@MapReturn</td>
		<td>Usado em Metodos</td>
		<td>Retorna as posi��es de um mapa, e monta o mapa</td>
	</tr>
	
	<tr>
		<td>@Invoker</td>
		<td>Usado em Fields</td>
		<td></td>
	</tr>
	
	<tr>
		<td>@Inject</td>
		<td>Usado em Fields</td>
		<td>Injeta um Dao na classe, podendo ser utilizado para a cria��o de graficos</td>
	</tr>
	
	
	<tbody>
</table>
					</div>
				</div>
			</div>
	
		</main>

	</jsp:attribute>
	
	<jsp:attribute name="js_custom">
		
	</jsp:attribute>
</tags:_Layout>