package hu.bme.dtt.conferenceportal.session;

import hu.bme.dtt.conferenceportal.dao.ArticleDao;
import hu.bme.dtt.conferenceportal.util.SimplePdf;
import hu.bme.dtt.conferenceportal.util.StateHolder;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
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
	private ArrayList<SimplePdf> files = new ArrayList<SimplePdf>();
	private int uploadsAvailable = 1;
	private boolean autoUpload = false;
	private boolean useFlash = false;
	private ArticleDao articleDao;

	@In(create = true)
	StateHolder<SimplePdf> simplePdfStateHolder;

	private static final Logger logger = Logger.getLogger(FileUploadBean.class);

	@Create
	public void init() {
		try {
			articleDao = InitialContext
					.doLookup("ConferencePortal-ear/articleDao/local");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getSize() {
		if (getFiles().size() > 0) {
			return getFiles().size();
		} else {
			return 0;
		}
	}

	public FileUploadBean() {
	}

	public void createPdf(OutputStream stream, Object object)
			throws IOException {
		logger.info("createPdf elindult.");
		// stream.write(getFiles().get((Integer) object).getData());
		// Object restoredData=SessionDataHelper.getDataByKey(object);
		if (null != stream && null != object) {
			logger.info("inside generatePdf method for pdf");
			byte[] buf = ((SimplePdf) object).getData();
			if (null != buf && buf.length > 0) {
				try {
					stream.write(buf);
					stream.close();
					stream.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

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
		files.add(pdf);
		simplePdfStateHolder.setSelected(pdf);
		uploadsAvailable--;
	}

	public String clearUploadData() {
		files.clear();
		setUploadsAvailable(1);
		return null;
	}

	public long getTimeStamp() {
		return System.currentTimeMillis();
	}

	public void save() {
		// articleDao.save(getPdf());
	}

	public ArrayList<SimplePdf> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<SimplePdf> files) {
		this.files = files;
	}

	public SimplePdf getPdf() {
		if (files.size() > 0) {
			return files.get(0);
		}
		return null;
	}

	public int getUploadsAvailable() {
		return uploadsAvailable;
	}

	public void setUploadsAvailable(int uploadsAvailable) {
		this.uploadsAvailable = uploadsAvailable;
	}

	public boolean isAutoUpload() {
		return autoUpload;
	}

	public void setAutoUpload(boolean autoUpload) {
		this.autoUpload = autoUpload;
	}

	public boolean isUseFlash() {
		return useFlash;
	}

	public void setUseFlash(boolean useFlash) {
		this.useFlash = useFlash;
	}

}
