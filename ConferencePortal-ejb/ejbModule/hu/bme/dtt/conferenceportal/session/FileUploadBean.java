package hu.bme.dtt.conferenceportal.session;

import hu.bme.dtt.conferenceportal.dao.ArticleDao;
import hu.bme.dtt.conferenceportal.dao.UserDao;
import hu.bme.dtt.conferenceportal.entity.Article;
import hu.bme.dtt.conferenceportal.util.SimplePdf;
import hu.bme.dtt.conferenceportal.util.StateHolder;

import java.io.Serializable;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Credentials;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

/**
 * @author Karcsi
 * 
 */
@Name("fileUploadBean")
@Scope(ScopeType.CONVERSATION)
public class FileUploadBean implements Serializable {

	private static final long serialVersionUID = 3792763850205269555L;
	private SimplePdf file;
	private Article newArticle;
	private ArticleDao articleDao;

	@In(create = true)
	StateHolder<SimplePdf> simplePdfStateHolder;

	@In
	Credentials credentials;

	private static final Logger logger = Logger.getLogger(FileUploadBean.class);

	@Create
	public void init() {
		file = null;
		newArticle = new Article();
		try {
			articleDao = InitialContext
					.doLookup("ConferencePortal-ear/articleDao/local");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FileUploadBean() {
	}

	// public void createPdf(OutputStream stream, Object object)
	// throws IOException {
	// logger.info("createPdf elindult.");
	// // stream.write(getFiles().get((Integer) object).getData());
	// // Object restoredData=SessionDataHelper.getDataByKey(object);
	// if (null != stream && null != object) {
	// logger.info("inside generatePdf method for pdf");
	// byte[] buf = ((SimplePdf) object).getData();
	// if (null != buf && buf.length > 0) {
	// try {
	// stream.write(buf);
	// stream.close();
	// stream.flush();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }

	public void listener(UploadEvent event) throws Exception {
		logger.info("listener elindult, event is null: " + (event == null));
		UploadItem item = event.getUploadItem();
		logger.info("item ok");
		SimplePdf pdf = new SimplePdf();
		byte[] data = item.getData();
		logger.info("data lekérve. data is ok: " + (data != null));
		pdf.setData(data);
		pdf.setLength((long) data.length);
		logger.info("length: " + pdf.getLength());
		pdf.setName(item.getFileName());
		file = pdf;
		simplePdfStateHolder.setSelected(pdf);
	}

	public String clearUploadData() {
		file = null;
		simplePdfStateHolder.setSelected(null);
		return null;
	}

	public long getTimeStamp() {
		return System.currentTimeMillis();
	}

	public void save() {
		logger.info("Cikk mentés elkezdõdött.");
		if (this.file != null) {
			logger.info("Cikk pdf-ként (is) fel lett töltve.");
			this.newArticle.setData(this.file.getData());
			this.newArticle.setFileName(this.file.getName());
			this.newArticle.setLength(this.file.getLength());
			this.newArticle.setMime(SimplePdf.getMime());
		}

		UserDao userDao = null;
		try {
			userDao = InitialContext
					.doLookup("ConferencePortal-ear/userDao/local");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.newArticle.setUser(userDao.getUser(credentials.getUsername()));
		if (articleDao.save(newArticle)) {
			logger.info("Cikk mentés befejezõdött.");
		} else {
			logger.info("Valami hiba történt a mentés közben!");
		}
	}

	/**
	 * @return the file
	 */
	public SimplePdf getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(SimplePdf file) {
		this.file = file;
	}

	/**
	 * @return the newArticle
	 */
	public Article getNewArticle() {
		return newArticle;
	}

	/**
	 * @param newArticle
	 *            the newArticle to set
	 */
	public void setNewArticle(Article newArticle) {
		this.newArticle = newArticle;
	}

}
