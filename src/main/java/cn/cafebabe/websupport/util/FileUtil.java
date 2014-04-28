package cn.cafebabe.websupport.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import java.io.*;

public class FileUtil
{
	private static final int BUFFER_SIZE = 16 * 1024;
	private final static Log log = LogFactory.getLog(FileUtil.class);

	public static void copyFileToPath(File file, String filePath)
	{
		if (file != null && file.isFile())
		{
			copy(file, new File(filePath));
		}
	}

	public static void copy(File src, File dst)
	{
		try
		{
			InputStream in = null;
			OutputStream out = null;
			try
			{
				in = new BufferedInputStream(new FileInputStream(src),
						BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst),
						BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0)
				{
					out.write(buffer);
				}
			}
			finally
			{
				if (null != in)
				{
					in.close();
				}
				if (null != out)
				{
					out.close();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 在服务器端创建文件夹,根据传入的文件名
	 * 目标文件路径传入的都是相对路径
	 * 源文件可以是文件对象，也可以是绝对路径
	 *
	 * @param filePath
	 */
	public static void createFile(String filePath)
	{
		filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1)
				+ DateUtil.getDateByFormat("yyyyMMdd") + "/";
		String path = ServletActionContext.getServletContext().getRealPath(
				filePath);
		File file = new File(path);
		if (!file.exists())
		{// 文件不存在
			file.mkdirs();
		}
	}

	/**
	 * 进行copy操作
	 *
	 * @param orginalPath
	 * @param aimPath
	 * @return
	 */
	public static String copyFile(String orginalPath, String aimPath)
	{
		try
		{
			createFile(aimPath);
			File orginFile = new File(orginalPath);// 源文件
			return copyFile(orginFile, aimPath);
		}
		catch (Exception e)
		{
			System.out.print("-----------copyFile---------" + e);
			return null;
		}
	}

	/**
	 * 进行copy操作
	 *
	 * @param orginalFile 源文件
	 * @param aimPath     目标
	 * @return
	 */
	public static String copyFile(File orginFile, String aimPath)
	{
		createFile(aimPath);
		aimPath = fileSavePateh(aimPath);
		String savePath = aimPath;
		aimPath = ServletActionContext.getServletContext().getRealPath(aimPath);
		String flag = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try
		{
			fis = new FileInputStream(orginFile);
			fos = new FileOutputStream(aimPath);
			byte[] buffer = new byte[1024];
			while ((fis.read(buffer) != -1))
			{
				fos.write(buffer, 0, buffer.length);
			}
			flag = savePath;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (fis != null)
					fis.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			try
			{
				if (fos != null)
					fos.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return flag;
	}

	public static void copyFile(InputStream stream, String fullPath)
	{
		createIfNotExist(fullPath);

		try (FileOutputStream fs = new FileOutputStream(fullPath))
		{
			byte[] buffer = new byte[BUFFER_SIZE];
			int byteRead = 0;
			while ((byteRead = stream.read(buffer)) != -1)
			{
				fs.write(buffer, 0, byteRead);
				fs.flush();
			}
			stream.close();
		}
		catch (Exception e)
		{
			log.error("上传文件失败", e);
		}
	}

	public static File createIfNotExist(String fullPath)
	{
		File file = new File(fullPath);
		if (!file.getParentFile().exists())
		{
			file.getParentFile().mkdirs();
		}
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
		return file;
	}

	/**
	 * 删除文件
	 * path 为相对路径
	 *
	 * @param aimPath
	 * @return
	 */
	public static void delete(String path)
	{
		path = ServletActionContext.getServletContext().getRealPath(path);
		File file = new File(path);
		delete(file);
	}

	public static void delete(File file)
	{
		String st_path = file.getAbsolutePath();
		System.out.println(st_path);
		file.delete();
		String st_name = st_path.substring(st_path.lastIndexOf("\\") + 1);
		st_path = st_path.replace(st_name, "st_" + st_name);
		File st_file = new File(st_path);
		if (st_file.exists())
		{
			st_file.delete();
		}
	}

	// 根据传入的图片路径返回真实的图片路径
	public static String fileSavePateh(String aimPath)
	{
		return aimPath.substring(0, aimPath.lastIndexOf("/") + 1)
				+ DateUtil.getDateByFormat("yyyyMMdd") + "/"
				+ DateUtil.getDateByFormat("HHmmssSSS")
				+ aimPath.substring(aimPath.lastIndexOf("."));
	}
}