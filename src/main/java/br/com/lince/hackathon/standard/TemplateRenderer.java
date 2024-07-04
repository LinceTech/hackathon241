package br.com.lince.hackathon.standard;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.helper.ConditionalHelpers;
import com.github.jknack.handlebars.helper.NumberHelper;
import com.github.jknack.handlebars.helper.StringHelpers;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Classe utilizada para inicializar o Handlebars com as configurações padrão da aplicação
 *
 * @param <T>
 */
public class TemplateRenderer<T> {
    private static final Handlebars handlebars = new Handlebars(new ClassPathTemplateLoader("/templates"));

    /**
     * Permite acessar a instância padrão do handlebars da aplicação, caso necessário utilizar fora de
     * <code>TemplateRenderer#render</code>
     *
     * @return instancia pré configurada do handlebars
     */
    public static Handlebars defaultHandlebars() {
        return handlebars;
    }

    /*
     * Configurar o template handlebars para incluir helpers padrão
     */
    static {
        StringHelpers.register(handlebars);
        NumberHelper.register(handlebars);
        for (var helper : ConditionalHelpers.values()) {
            handlebars.registerHelper(helper.name(), helper);
        }
    }

    /**
     * @param template nome do arquivo sem a extensão
     * @param response servlet response onde será escrito o html renderizado
     */
    public TemplateRenderer(String template, HttpServletResponse response) {
        this.template = template;
        this.response = response;
    }

    private final String template;
    private final HttpServletResponse response;

    /**
     * Executa o template para renderizar o html
     *
     * @param params parâmetros utilizados no template
     * @throws IOException exceção pode ser lançada ao escrever o html gerado no HttpServletResponse
     */
    public void render(T params) throws IOException {
        final var templateObject = handlebars.compile(template);
        response.setContentType("text/html");
        response.getWriter().write(templateObject.apply(params));
    }
}
