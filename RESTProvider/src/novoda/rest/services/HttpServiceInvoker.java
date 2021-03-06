package novoda.rest.services;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import novoda.mixml.XMLNode;
import novoda.rest.clag.command.ClagInitCommand;
import novoda.rest.clag.command.ClagQueryCommand;
import novoda.rest.clag.command.ClagXtifyInitCommand;
import novoda.rest.context.command.Command;
import novoda.rest.database.ModularSQLiteOpenHelper;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.xml.sax.SAXException;

import android.content.Intent;

// dispatcher
public class HttpServiceInvoker extends HttpService {

	public static final String CONTENT_TYPE_JSON = "application/json";

	@Override
	public void onCreate() {
		super.onCreate();
	}

	/*
	 * We can have several content type for XML. For instance application/xml or
	 * text/xml or text/rss+xml etc... as such we only check for XML prefix
	 */
	public static final String CONTENT_TYPE_XML_SUFFIX = "xml";

	public static final int JSON = 0;

	public static final int XML = 1;

	@SuppressWarnings("unchecked")
	@Override
	protected void onHandleResponse(HttpResponse response, HttpContext context) {
		Intent intent = getIntent().getParcelableExtra("intent");
		@SuppressWarnings("rawtypes")
		Command c = getCommand(intent, 0);
		InputStream in;
		try {
			if (response.getEntity().getContentType().getValue()
					.equals(CONTENT_TYPE_JSON)) {
				in = response.getEntity().getContent();
				JsonNode node = new ObjectMapper().readTree(in);
				c.execute(node);
			} else if (response.getEntity().getContentType().getValue()
					.endsWith(CONTENT_TYPE_XML_SUFFIX)) {
				in = response.getEntity().getContent();
				XMLNode node = new XMLNode();
				node.parse(in);
				c.execute(node);
			} else {
				in = response.getEntity().getContent();
				XMLNode node = new XMLNode();
				node.parse(in);
				c.execute(node);
//				Log.i("TEST", EntityUtils.toString(response.getEntity()));
//				throw new ParserException(
//						"the content type is not defined nor did you define it in the service description");
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPreCall(HttpUriRequest request, HttpContext context) {
		Intent intent = getIntent().getParcelableExtra("intent");
		// if (intent != null
		// && intent.getAction().equals("novoda.rest.clag.REGISTER_XTIFY")
		// || intent.getAction().equals("novoda.rest.clag.QUERY")) {
		// request.addHeader("account", intent.getStringExtra("account"));
		// }
		super.onPreCall(request, context);
	}

	public Command<?> getCommand(Intent intent, int type) {
		// QueryCallInfo info = intent.getParcelableExtra("callinfo");
		if (intent.getAction().equals("novoda.rest.clag.INIT")) {
			ClagInitCommand c = new ClagInitCommand();
			c.setPersister(new ModularSQLiteOpenHelper(getBaseContext()));
			return c;
		} else if (intent.getAction().equals("novoda.rest.clag.REGISTER_XTIFY")) {
			ClagXtifyInitCommand c = new ClagXtifyInitCommand();
			// c.setPersister(new ModularSQLiteOpenHelper(getBaseContext()));
			return c;

		} else if (intent.getAction().equals("novoda.rest.clag.QUERY")) {
			ClagQueryCommand c = new ClagQueryCommand();
			c.setPersister(new ModularSQLiteOpenHelper(getBaseContext()));
			return c;
		}
		return null;
	}

}
