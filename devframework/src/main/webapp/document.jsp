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
                    <li class="breadcrumb-item active" aria-current="page">Documentation</li>
                </ol>
            </nav>

            <section class="jumbotron text-center">
                <div class="container">
                    <h1 class="jumbotron-heading"><strong>Documentation</strong> </h1>
                </div>
            </section>

            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <p>
                            Documentation on how to implement the classes for uploading on the platform.
                        </p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        &nbsp;<br />&nbsp;<br />
                    </div>
                </div>
            </div>

        </main>

    </jsp:attribute>

    <jsp:attribute name="js_custom">

    </jsp:attribute>
</tags:_Layout>