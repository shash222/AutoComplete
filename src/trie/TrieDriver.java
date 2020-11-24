package trie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
//import com.google.gson.*;
/**
 * Servlet implementation class TrieDriver
 */
@WebServlet("/autocomplete")
public class TrieDriver extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Trie trie=null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrieDriver() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		if (trie==null) {
			trie=new Trie();
			File file=new File("C:\\Users\\sman0\\git\\AutoComplete-and-SpellCheck\\SpellCheck\\trieTest.txt");
			Scanner in=new Scanner(file);
			String str;
			while(in.hasNextLine()) {
				str=in.nextLine();
				trie.add(str);
			}
			in.close();
		}
		*/
		String wordsListParam = request.getParameter("words");
		String sanitizedWordsListParam;
		if (wordsListParam.charAt(0) == '[')
			sanitizedWordsListParam = wordsListParam.substring(1, wordsListParam.length() - 1);
		else
			sanitizedWordsListParam = wordsListParam;
		String[] wordsList = sanitizedWordsListParam.split(",");
		trie = new Trie();
		for (String s : wordsList) {
			trie.add(s.trim());
		}
		// trie.add("test1");
		// trie.add("test2");
		// trie.add("tabcd");
		// trie.add("tbcde");
		// String entry=request.getQueryString();
		String entry = request.getParameter("search");
		List<String> words=trie.getWords(entry);
		String json="[\n";
		for (int i=0;i<words.size();i++) {
			json+="{\"word\":\""+words.get(i)+"\"}";
			if (i<words.size()-1) {
				json+=",";
			}
			json+="\n";
		}
		json+="]";
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		out.write(json);
	}
	
}
