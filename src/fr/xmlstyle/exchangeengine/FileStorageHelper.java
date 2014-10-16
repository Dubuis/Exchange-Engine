package fr.xmlstyle.exchangeengine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
/**
 * @author DevHackSecure.fr / NameX
 * You can copy this class but you have to keep the author name
 * in the file.
 */
public class FileStorageHelper {
	public static final int INTERNAL_STORAGE_MODE = 0;
	public static final int EXTERNAL_STORAGE_MODE = 1;
	private Context mContext;
	private String mFileName;
	private String mFilePath;
	private String mFileFullPath;
	private File mFile;
	private int mMode;
	public FileStorageHelper(Context mContext, String mFileName, String mFilePath, int mMode) {
		super();
		this.mContext = mContext;
		this.mFileName = mFileName;
		this.mMode = mMode;
		if (this.mMode == INTERNAL_STORAGE_MODE)
			this.mFileFullPath = this.mFileName;
		else if (this.mMode == EXTERNAL_STORAGE_MODE)
			this.mFileFullPath = mFilePath + this.mFileName;
	}
	public FileStorageHelper(Context mContext, String mFileName, int mMode) {
		super();
		this.mContext = mContext;
		this.mFileName = mFileName;
		this.mMode = mMode;
		if (this.mMode == INTERNAL_STORAGE_MODE)
			this.mFileFullPath = this.mFileName;
		else if (this.mMode == EXTERNAL_STORAGE_MODE){
			this.mFilePath = Environment.getExternalStorageDirectory().getPath();
			this.mFileFullPath = this.mFilePath + "/" + this.mFileName;
		}
	}
	public void createFile(){
		// Création d'un dossier si on est en mode EXTERNE
		if (this.mMode == EXTERNAL_STORAGE_MODE){
			File dir = new File(this.mFilePath);
			dir.mkdirs();
		}
		// On prepare le fichier
		this.mFile = new File(this.mFileFullPath);
		try {
			// Creer un fichier s'il n'existe pas
			this.mFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeInExternalFile(String stringToWrite){
			try {
				// Ouverture du flux d'écriture
				FileOutputStream output = new FileOutputStream(this.mFile);
				// On ecrit les données
				output.write(stringToWrite.getBytes());
				// On oubli pas de fermer le flux
				output.close();
			} catch (FileNotFoundException e) {
				// Fichier non trouvé
				e.printStackTrace();
			} catch (IOException e) {
				// Les autres IO exception
				e.printStackTrace();
			}
	}
	public String readInExternalFile(){
		try {
			// Ouverture du flux de lecture du fichier
			FileInputStream input = new FileInputStream(this.mFile);
			InputStreamReader isr = new InputStreamReader(input);
			char[] buffer = new char[255];
			// On rempli le buffer par ce qu'il y a dans le fichier
			isr.read(buffer);
			// On transforme notre tableau de char en String
			String readData = new String(buffer);
			// On oubli pas de fermer le flux
			isr.close();
			return readData;
		} catch (FileNotFoundException e) {
			// Fichier non trouvé
			e.printStackTrace();
		} catch (IOException e) {
			// Les autres IO exception
			e.printStackTrace();
		}
		return null;
	}
	public void writeInInternalFile(String stringToWrite) {
		try {
			// Ouverture du flux d'écriture
			FileOutputStream output = this.mContext.openFileOutput(this.mFileFullPath, Context.MODE_PRIVATE);
			// On ecrit les données
			output.write(stringToWrite.getBytes());
			// On oubli pas de fermer le flux
			output.close();
		} catch (FileNotFoundException e) {
			// Fichier non trouvé
			e.printStackTrace();
		} catch (IOException e) {
			// Les autres IO exception
			e.printStackTrace();
		}
	}
	public String readInInternalFile(){
		try {
			// Ouverture du flux de lecture du fichier
			FileInputStream input = this.mContext.openFileInput(this.mFileFullPath);
			InputStreamReader isr = new InputStreamReader(input);
			char[] buffer = new char[255];
			// On rempli le buffer par ce qu'il y a dans le fichier
			isr.read(buffer);
			// On transforme notre tableau de char en String
			String readData = new String(buffer);
			// On oubli pas de fermer le flux
			isr.close();
			return readData;
		} catch (FileNotFoundException e) {
			Log.i("FSH", "File not found !");
			// Fichier non trouvé
			e.printStackTrace();
		} catch (IOException e) {
			// Les autres IO exception
			e.printStackTrace();
		}
		return null;
	}
	public String getFileName() {
		return mFileName;
	}
	public String getFilePath() {
		return mFilePath;
	}
	public void setFileName(String mFileName) {
		this.mFileName = mFileName;
	}
	public void setFilePath(String mFilePath) {
		this.mFilePath = mFilePath;
	}
}