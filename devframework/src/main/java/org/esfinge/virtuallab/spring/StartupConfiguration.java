package org.esfinge.virtuallab.spring;

public class StartupConfiguration {

//    @EventListener
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        try {
//            // carrega as classes/jar de servico do diretorio de upload
//            // para ficarem disponiveis para a aplicacao
//            for (File file : PersistenceService.getInstance().getUploadedFiles()) {
//                try {
//                    // carrega o servico
//                    Class<?> serviceClass = ClassLoaderService.getInstance().loadService(file);
//
//                    // se for um servico DAO, informa ao EntityManagerFactoryHelper para mapear as entidades
//                    if (MetadataHelper.getInstance().getClassMetadata(serviceClass).isServiceDAO()) {
//                        EntityManagerFactoryHelper.getInstance().mapEntitiesFromJar(file.toURI().toURL());
//                    }
//                } catch (Exception exc) {
//                    // TODO: debug..
//                    exc.printStackTrace();
//                }
//            }
//        } catch (Exception e1) {
//            // TODO: debug..
//            e1.printStackTrace();
//        } finally {
//            // carrega o EntityManagerFactory com as entidades mapeadas
//            EntityManagerFactoryHelper.getInstance().loadEntities();
//        }
//    }
}
