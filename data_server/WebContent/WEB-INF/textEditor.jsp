<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html >
<head>
  <meta charset="UTF-8">
  <title>TextEdit</title>
        <link rel="stylesheet" href="form.css">  
</head>

<body>



<div>
	<div id="divProjectFile" class="bordered">
		<label>Project:</label>
		<div>
			<label>Name:</label>
			<input id="inputProjectName" onchange="inputProjectName_Changed(this);"></input>
		</div>
		<button onclick="buttonProjectSave_Clicked();">Save</button>
		<button onclick="buttonProjectLoad_Clicked();">Load:</button>
		<input id="inputFileProjectToLoad" type="file"></input>
		<button onclick="buttonProjectNew_Clicked();">New</button>
	</div>

	<div id="divDocumentList" class="bordered">
		<label>Documents:</label>
		<div>
			<button onclick="buttonDocumentNew_Clicked();">New</button>		
			<button onclick="buttonDocumentExistingAdd_Clicked();">Add Existing:</button>
			<input id="inputFileDocumentExistingToAdd" type="file"></input>
			<button onclick="buttonDocumentSelectedRemove_Clicked();">Remove Selected</button>
		</div>
		<select id="selectDocumentsInProject" style="width:100%" size="10" onchange="selectDocumentsInProject_Changed(this);"></select>
	</div>

	<div id="divDocumentSelected" class="bordered">
		<div><label>Document Selected:</label></div>
		<div>
			<label>Name:</label>
			<input id="inputDocumentSelectedName" onchange="inputDocumentSelectedName_Changed(this);"></input>
			<button onclick="buttonDocumentSelectedSave_Clicked();">Save</button>
			<label>Cursor:</label>
			<label>Row:</label>
			<input id="inputCursorRow" class="number" type="number" onchange="inputCursorColumnOrRow_Changed(this);"></input>
			<label>Column:</label>
			<input id="inputCursorColumn" class="number" type="number" onchange="inputCursorColumnOrRow_Changed(this);"></input>

		</div>
		<div><label>Contents:</label></div>
		<div><textarea id="textareaDocumentSelectedContents" style="width:100%" rows="20" onchange="textareaDocumentSelectedContents_Changed(this);" onkeydown="textareaDocumentSelectedContents_CursorMoved(this);" onmousedown="textareaDocumentSelectedContents_CursorMoved(this);"></textarea></div>
	</div>

	<div id="divSearch" class="bordered">
		<div>
			<button onclick="buttonSearch_Clicked();">Search for:</button>
			<input id="inputTextToSearchFor"></input>
			<input id="checkboxSearchMatchCase" type="checkbox">Match Case</input>
		</div>
		<div>
			<div><label>Results:</label></div>
			<select id="selectSearchResults" size="8" style="width:100%" onchange="selectSearchResults_Changed(this);"></select>
		</div>
		
	</div>

	</div>


    <script type="text/javascript" src="textEditor.js"></script>

</body>
</html>