
import org.esfinge.virtuallab.TestUtils;
import org.esfinge.virtuallab.demo.polyglot.Address;
import org.esfinge.virtuallab.demo.polyglot.Person;
import org.esfinge.virtuallab.demo.polyglot.PolyglotExample;

public class Main {

    public static void main(String... args) throws Exception {
        TestUtils.createJar("polyglotBase.jar", Address.class, Person.class, PolyglotExample.class);
//        TestUtils.createJar("tarefa.jar", TarefaService.class, Tarefa.class);
//        TestUtils.createJar("matematica.jar", MatematicaService.class, MatematicaInvokerProxy.class, Ponto.class);
//        TestUtils.createJar("chart.jar", ChartService.class, Temperatura.class);
//        TestUtils.createJar("temperaturaDAO.jar", TemperaturaService.class, TemperaturaServiceProxy.class, Temperatura.class);
//        TestUtils.createJar("topicDAO.jar", TopicService.class, Topic.class);
//        TestUtils.createJar("INPE_Goes.jar", DaoXrayLow.class, XrayDataLow.class);
        /*
		Temperatura t = new Temperatura(2l, "-23.5475","-46.63611111", 28.2, 19.3, "janeiro");

		JexlEngine jexl = new JexlBuilder().cache(512).silent(false).create();
		String rawText = "Temperatura do Mes '${temp.mes.toUpperCase()}' / '${temp.mes}'";
		JexlExpression exp = jexl.createExpression(parseEL(rawText));
		JexlContext context = new MapContext();
		context.set("temp", t);

		System.out.println(exp.evaluate(context));
         */

 /*
		ELProcessor elp = new ELProcessor();
		elp.defineBean("temp", t);
		System.out.println(elp.getValue("'Temperatura do Mes \\'' + temp.mes + '\\''", String.class));
//		System.out.println(elp.eval(parseEL("Temperatura do Mes '${temp.mes}'")));
         */
 /*
		Enhancer proxy = new Enhancer();
		proxy.setSuperclass(MatematicaService.class);
		proxy.setCallback(InvokerService.getInstance());

		MatematicaService matSvc = (MatematicaService) proxy.create();
		System.out.println(matSvc.calcularDistancia(new Ponto(1,2), new Ponto(4,6)));
         */
 /*
		for ( Method m : MethodUtils.getMethodsWithAnnotation(Main.class, BarChartReturn.class) )
		{
			MethodReturnProcessor<?> processor = MethodReturnProcessorHelper.getInstance().findProcessor(m);
			JsonData data = processor.process(m.invoke(null, null));
			System.out.println(data);
			System.out.println();
		}
         */
    }

    private static String parseEL(String text) {
        // procura por ' e adiciona escape
        System.out.println("> TEXT: " + text);
        var str = text.trim().replaceAll("'", "\\\\'");
        System.out.println("> STEP 1: " + str);

        str = str.replaceAll("\\$\\{(.+?)\\}", "' + $1 + '");
        System.out.println("> STEP 2: " + str);

//		str = str.replaceAll("\\$\\{(.+)\\}$", "' + $1 + '");
//		System.out.println("> STEP 3: " + str);
        str = "'" + str + "'";
        System.out.println("> STEP 4: " + str);

//		System.out.println(str);
        return str;
    }

//    /**
//     * Retorna uma String com as informacoes de bytecode de uma classe.
//     *
//     * @param classBytecode os bytecodes da classe
//     * @return as informacoes de bytecode da classe informada
//     */
//    public static String inspectToString(byte[] classBytecode) {
//        var classReader = new ClassReader(classBytecode);
//        var buffer = new StringWriter();
//        var tracer = new TraceClassVisitor(new PrintWriter(buffer));
//        classReader.accept(tracer, 0);
//
//        return (buffer.toString());
//    }
}
