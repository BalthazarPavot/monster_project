package database;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.*;

public class DatabaseInitializer {

	    private static final String DEFAULT_DELIMITER = ";";

	    private Connection connection;
	    
	    private PrintWriter logWriter = new PrintWriter(System.out);
	    private PrintWriter errorLogWriter = new PrintWriter(System.err);

	    private String delimiter = DEFAULT_DELIMITER;
	    private boolean fullLineDelimiter = false;

	    public DatabaseInitializer (Connection connection) {
	        this.connection = connection;
	    }

	    public void setLogWriter(PrintWriter logWriter) {
	        this.logWriter = logWriter;
	    }

	    public void setErrorLogWriter(PrintWriter errorLogWriter) {
	        this.errorLogWriter = errorLogWriter;
	    }

	    public void runScript(Reader reader) throws IOException, SQLException {
	        try {
	                runScript(connection, reader);
	       
	        } catch (IOException e) {
	            throw e;
	        } catch (SQLException e) {
	            throw e;
	        } catch (Exception e) {
	            throw new RuntimeException("Error running script.  Cause: " + e, e);
	        }
	    }

	    private void runScript(Connection conn, Reader reader) throws IOException,
	            SQLException {
	        StringBuffer command = null;
	        try {
	            LineNumberReader lineReader = new LineNumberReader(reader);
	            String line = null;
	            while ((line = lineReader.readLine()) != null) {
	                if (command == null) {
	                    command = new StringBuffer();
	                }
	                String trimmedLine = line.trim();
	                if (trimmedLine.startsWith("--")) {
	                    println(trimmedLine);
	                } else if (trimmedLine.length() < 1
	                        || trimmedLine.startsWith("//")) {
	                    // Do nothing
	                } else if (trimmedLine.length() < 1
	                        || trimmedLine.startsWith("--")) {
	                    // Do nothing
	                } else if (!fullLineDelimiter
	                        && trimmedLine.endsWith(getDelimiter())
	                        || fullLineDelimiter
	                        && trimmedLine.equals(getDelimiter())) {
	                    command.append(line.substring(0, line
	                            .lastIndexOf(getDelimiter())));
	                    command.append(" ");
	                    Statement statement = conn.createStatement();

	                    println(command);

	                    boolean hasResults = false;
	                        try {
	                            statement.execute(command.toString());
	                        } catch (SQLException e) {
	                            e.fillInStackTrace();
	                            printlnError("Error executing: " + command);
	                            printlnError(e);
	                        }


	                    ResultSet rs = statement.getResultSet();
	                    if (hasResults && rs != null) {
	                        ResultSetMetaData md = rs.getMetaData();
	                        int cols = md.getColumnCount();
	                        for (int i = 0; i < cols; i++) {
	                            String name = md.getColumnLabel(i);
	                            print(name + "\t");
	                        }
	                        println("");
	                        while (rs.next()) {
	                            for (int i = 0; i < cols; i++) {
	                                String value = rs.getString(i);
	                                print(value + "\t");
	                            }
	                            println("");
	                        }
	                    }

	                    command = null;
	                    try {
	                        statement.close();
	                    } catch (Exception e) {
	                    	e.printStackTrace();
	                    }
	                    Thread.yield();
	                } else {
	                    command.append(line);
	                    command.append(" ");
	                }
	            }

	        } catch (SQLException e) {
	            e.fillInStackTrace();
	            printlnError("Error executing: " + command);
	            printlnError(e);
	            throw e;
	        } catch (IOException e) {
	            e.fillInStackTrace();
	            printlnError("Error executing: " + command);
	            printlnError(e);
	            throw e;
	        } finally {
	            flush();
	        }
	    }

	    private String getDelimiter() {
	        return delimiter;
	    }

	    private void print(Object o) {
	        if (logWriter != null) {
	            System.out.print(o);
	        }
	    }

	    private void println(Object o) {
	        if (logWriter != null) {
	            logWriter.println(o);
	        }
	    }

	    private void printlnError(Object o) {
	        if (errorLogWriter != null) {
	            errorLogWriter.println(o);
	        }
	    }

	    private void flush() {
	        if (logWriter != null) {
	            logWriter.flush();
	        }
	        if (errorLogWriter != null) {
	            errorLogWriter.flush();
	        }
	    }
	}
