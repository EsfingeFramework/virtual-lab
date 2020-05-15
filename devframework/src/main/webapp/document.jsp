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
                  	<p>No momento que for utilizar a ferramenta pela primeira vês ela estará assim:<br>
                      <img src="resources/images/virtuallab.png" alt="virtualLabGui" width=1024></p>
                 <p> Em seguida vai em adicionar nova classe e adicione as classes ou jars que necessita para utilizar a ferramenta.<br>
                      <img src="resources/images/virtuallab_envio.png" alt="virtualLabGui" width=1024></p>
                  <p>Este por sua vês vai abrir essa janela.</p>
                  <p>Selecione Browse e selecione o arquivo.</p>
                  <img src="resources/images/virtuallab_envio_incluir.png" alt="virtualLabGui" width=1024 ><br>
                  <p>Por enquanto o tipo de arquivo que pode ser selecionado é java</p>
                  <p>Clique em abrir para abrir</p>
                  <img src="resources/images/virtuallab_envio_main.png" alt="virtualLabGui" width=1024><br>
                  <p>Selecione a classe que vai ser aberta</p>
                  <img src="resources/images/virtuallab_envio_upload.png" alt="virtualLabGui" width=1024><br>
				  <p>Selecione o método que vai ser executado</p>
				  <img src="resources/images/virtuallab_selectMethod.png" alt="virtualLabGui" width=1024><br>
				  <p>Se não tiver nenhum paramentro clique em executar, caso contrario, preencher com os parâmetros.</p>
				  <img src="resources/images/virtuallab_executar.png" alt="virtualLabGui" width=1024><br>
				  <p>Vai ser mostrado o resultado a seguir.</p>
				  <img src="resources/images/virtuallab_resultado.png" alt="virtualLabGui" width=1024><br>
				  
				</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<h3>Desenvolvimento utilizando a ferramenta</h3>
						<h4>Conexão ao banco de dados utilizando <code>@ServiceDAO</code></h4>
						<p>Cria a classe entidade padrão do JPA</p>
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
						
<p>Coloca a anotação <code>@ServiceDAO</code> ela contem os parametros <code>label</code> que é uma label,<code>description</code> que é a descrição da classe,
<code>url</code> a url do jbdc nesse exemplo url = "jdbc:postgresql://localhost:5432/postgres",
 <code>user</code> usuario do banco de dados,
  <code>password</code> password do banco de dados, <code>dialect</code> qual é o banco de dados que está sendo utilizado.
</p>						
						<pre>
							<code>

@ServiceDAO(
	label = "DAO",
	description = "Demonstração da anotação @ServiceDAO.",
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
		label = "Listar temperaturas por mês",
		description = "Retorna as temperaturas referentes ao mês informado.")
	@TableReturn
	public List&lt;Temperatura&gt;getTemperaturaByMes(String mes);

	
	@ServiceMethod(
		label = "Listar temperaturas máximas",
		description = "Retorna as temperaturas com máxima igual ou maior ao valor informado.")
	@TableReturn
	public List&lt;Temperatura&gt; getTemperaturaByMaximaOrderByMaximaAsc(@GreaterOrEquals double temp);

	
	@ServiceMethod(
			label = "Listar temperaturas mínimas",
			description = "Retorna as temperaturas com mínima igual ou menor ao valor informado.")
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
	label = "GRÁFICOS - BARRAS",
	description = "Demonstração da anotação @BarChartReturn.")
public class BarChartDemo
{
	
		@ServiceMethod(
		label = "Criar gráfico - Lista",
		description = "@BarChartReturn sem parâmetros.")
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
<h4>Lista de anotações</h4>
<p>Segue aqui uma tabela com a lista das anotações</p>
<table class="table">
	<caption>Lista de anotações do VirtualLab</caption>
	<thead>
	<tr>
		<th>Anotação</th>
		<th>Uso</th>
		<th>Descrição<br></th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td>@ServiceDAO</td>
		<td>Usado em Interfaces</td>
		<td>Usado para definir uma conexção com o banco de dados utilizando o Esfinge QueryBuilder</td>
	</tr>
	<tr>
		<td>@ServiceClass</td>
		<td>Usado em Classe</td>
		<td>Usado para definir se a classe é visivel para ser utilizado com a aplicação</td>
	</tr>
	<tr>
		<td>@ServiceMethod</td>
		<td>Usado em Metodos</td>
		<td>Utilizado para definir os métodos visiveis a aplicação</td>
	</tr>
	<tr>
		<td>@TableReturn</td>
		<td>Usado em Metodos</td>
		<td>Junto com o @ServiceMethod é utilizado para a visualização em forma de tabela</td>
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
		<td>Retorna as posições de um mapa, e monta o mapa</td>
	</tr>
	
	<tr>
		<td>@Invoker</td>
		<td>Usado em Fields</td>
		<td></td>
	</tr>
	
	<tr>
		<td>@Inject</td>
		<td>Usado em Fields</td>
		<td>Injeta um Dao na classe, podendo ser utilizado para a criação de graficos</td>
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