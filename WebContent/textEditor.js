function buttonDocumentExistingAdd_Clicked()
{
	var project = Globals.Instance.session.project;

	var inputFileDocumentExistingToAdd = document.getElementById
	(
		"inputFileDocumentExistingToAdd"
	);
	var fileToLoad = inputFileDocumentExistingToAdd.files[0];
	if (fileToLoad == null)
	{
		alert("Please choose a file with the accompanying 'Choose File' button first.");
	}
	else
	{
		var documentAdded = FileHelper.loadFileAsText
		(
			fileToLoad,
			buttonDocumentExistingAdd_Clicked_FileLoaded // callback
		);
	}
}

function buttonDocumentExistingAdd_Clicked_FileLoaded(fileLoadedName, fileLoadedContents)
{
	var project = Globals.Instance.session.project;

	var documentToAdd = new Document
	(
		fileLoadedName,
		fileLoadedContents
	);
	project.documentAdd(documentToAdd);

	project.domUpdate();
}

function buttonDocumentNew_Clicked()
{
	var project = Globals.Instance.session.project;

	project.documentNew();

	project.domUpdate();
}

function buttonDocumentSelectedRemove_Clicked()
{
	var project = Globals.Instance.session.project;
	
	var documentSelected = project.documentSelected();
	if (documentSelected != null)
	{
		project.documentRemove(documentSelected);
	}

	project.domUpdate();
}

function buttonDocumentSelectedSave_Clicked()
{
	var project = Globals.Instance.session.project;
	
	var documentSelected = project.documentSelected();
	if (documentSelected != null)
	{
		FileHelper.saveTextAsFile
		(
			documentSelected.contents, documentSelected.name
		);
	}

	project.domUpdate();
}

function buttonProjectLoad_Clicked()
{
	var project = Globals.Instance.session.project;

	var inputFileProjectToLoad = document.getElementById
	(
		"inputFileProjectToLoad"
	);
	var fileToLoad = inputFileProjectToLoad.files[0];
	if (fileToLoad == null)
	{
		alert("Please choose a file with the accompanying 'Choose File' button first.");
	}
	else
	{
		FileHelper.loadFileAsBytes
		(
			fileToLoad,
			buttonProjectLoad_Clicked_FileLoaded // callback
		);
	}
}

function buttonProjectLoad_Clicked_FileLoaded(fileLoadedName, fileLoadedContentsAsBytes)
{
	var projectAsTar = TarFile.fromBytes(fileLoadedName, fileLoadedContentsAsBytes);
	var documentsAsTarEntries = projectAsTar.entries;
	var documents = [];

	for (var i = 0; i < documentsAsTarEntries.length; i++)
	{
		var documentAsTarEntry = documentsAsTarEntries[i];
		var documentName = documentAsTarEntry.header.fileName;
		var documentContentsAsBytes = documentAsTarEntry.dataAsBytes;
		var documentContentsAsString = ByteHelper.bytesToStringUTF8(documentContentsAsBytes);
		var document = new Document(documentName, documentContentsAsString);
		documents.push(document);
	}

	var projectLoaded = new Project
	(
		fileLoadedName,
		documents
	);

	Globals.Instance.session.project = projectLoaded;

	projectLoaded.domUpdate();
}

function buttonProjectNew_Clicked()
{
	var projectNew = new Project
	(
		"Untitled.tar", []
	);

	Globals.Instance.session.project = projectNew;

	projectNew.domUpdate();

}


function buttonProjectSave_Clicked()
{
	var project = Globals.Instance.session.project;
	
	var projectAsTarFile = project.toTarFile();
	var projectAsBytes = projectAsTarFile.toBytes();
	FileHelper.saveBytesAsFile(projectAsBytes, project.name);
}

function buttonSearch_Clicked()
{
	var project = Globals.Instance.session.project;
	
	var inputTextToSearchFor = document.getElementById
	(
		"inputTextToSearchFor"
	);
	var textToSearchFor = inputTextToSearchFor.value;

	var checkboxSearchMatchCase = document.getElementById
	(
		"checkboxSearchMatchCase"
	);
	var matchCase = checkboxSearchMatchCase.checked;

	project.searchForText(textToSearchFor, matchCase);

	project.domUpdate();
}

function inputDocumentSelectedName_Changed(inputDocumentSelectedName)
{
	var project = Globals.Instance.session.project;

	var documentSelected = project.documentSelected();
	if (documentSelected != null)
	{
		var valueFromDOM = inputDocumentSelectedName.value;
		documentSelected.name = valueFromDOM;
	}

	//project.domUpdate();	
}

function inputCursorColumnOrRow_Changed()
{
	var project = Globals.Instance.session.project;

	var documentSelected = project.documentSelected();
	if (documentSelected != null)
	{
		var inputCursorColumn = document.getElementById("inputCursorColumn");
		var inputCursorRow = document.getElementById("inputCursorRow");

		documentSelected.cursorPos.x = Math.floor(inputCursorColumn.value);
		documentSelected.cursorPos.y = Math.floor(inputCursorRow.value);

		project.domUpdate_Cursor_Place();
	}
}

function inputProjectName_Changed(inputProjectName)
{
	var project = Globals.Instance.session.project;

	var valueFromDOM = inputProjectName.value;
	documentSelected.name = valueFromDOM;

	//project.domUpdate();	
}

function selectDocumentsInProject_Changed(selectDocumentsInProject)
{
	var project = Globals.Instance.session.project;

	var documentIndex = selectDocumentsInProject.selectedIndex;
	project.documentIndexSelected = documentIndex;

	project.domUpdate();
}

function selectSearchResults_Changed(selectSearchResults)
{
	var project = Globals.Instance.session.project;

	var searchResultSelectedIndex = selectSearchResults.selectedIndex;
	var searchResultSelected = project.searchResults[searchResultSelectedIndex];
	if (searchResultSelected != null)
	{
		var documentName = searchResultSelected.documentName;
		var documentToSelect = project.documents[documentName];
		var documentIndex = project.documents.indexOf(documentToSelect);
		project.documentIndexSelected = documentIndex;
		documentToSelect.cursorPos.overwriteWith(searchResultSelected.posInDocument);
		project.domUpdate_Cursor_Place();
	}	


}

function textareaDocumentSelectedContents_Changed(textareaDocumentSelectedContents)
{
	var project = Globals.Instance.session.project;

	var documentSelected = project.documentSelected();
	if (documentSelected != null)
	{
		var valueFromDOM = textareaDocumentSelectedContents.value;
		documentSelected.contents = valueFromDOM;
	}

	//project.domUpdate();
}

function textareaDocumentSelectedContents_CursorMoved(textareaDocumentSelectedContents)
{
	var project = Globals.Instance.session.project;

	var documentSelected = project.documentSelected();
	if (documentSelected != null)
	{
		var text = textareaDocumentSelectedContents.value;
		var cursorOffsetInChars = textareaDocumentSelectedContents.selectionEnd;

		var cursorPosNew = Document.stringAndCharOffsetToCursorPos
		(
			text,
			cursorOffsetInChars
		);

		documentSelected.cursorPos.overwriteWith(cursorPosNew);

		project.domUpdate_Cursor();
	}
}

// main

function main()
{
	var welcomeDocumentContents = "";

	for (var i = 0; i <5; i++)
	{
		welcomeDocumentContents += "ceci est un test\n";
	}

	var projectDemo = new Project
	(
		"Welcome.tar",
		[
			new Document
			(
				"Welcome.txt", 
				welcomeDocumentContents
			),
			new Document
			(
				"Welcome2.txt", 
				welcomeDocumentContents
			)

		]
	);
	Globals.Instance.initialize(projectDemo);	
}

// extensions

function ArrayExtensions()
{
	// extension class
}
{
	Array.prototype.addLookups = function(keyName)
	{
		for (var i = 0; i < this.length; i++)
		{
			var element = this[i];
			var key = element[keyName];
			this[key] = element;
		}

		return this;
	}

	Array.prototype.remove = function(elementToRemove)
	{
		this.splice(this.indexOf(elementToRemove), 1);
	}
}

function StringExtensions()
{
	// extension class
}
{
	String.prototype.padLeft = function(lengthToPadTo, charToPadWith)
	{
		var returnValue = this;

		while (returnValue.length < lengthToPadTo)
		{
			returnValue = charToPadWith + returnValue;
		}

		return returnValue;
	}


	String.prototype.padRight = function(lengthToPadTo, charToPadWith)
	{
		var returnValue = this;

		while (returnValue.length < lengthToPadTo)
		{
			returnValue += charToPadWith;
		}

		return returnValue;
	}
}

// classes

function ByteHelper()
{
	// static class
}
{
	ByteHelper.stringUTF8ToBytes = function(stringToConvert)
	{
		var bytes = [];

		for (var i = 0; i < stringToConvert.length; i++)
		{
			var byte = stringToConvert.charCodeAt(i);
			bytes.push(byte);
		} 

		return bytes;	
	}

	ByteHelper.bytesToStringUTF8 = function(bytesToConvert)
	{
		var returnValue = "";

		for (var i = 0; i < bytesToConvert.length; i++)
		{
			var byte = bytesToConvert[i];
			var byteAsChar = String.fromCharCode(byte);
			returnValue += byteAsChar
		}

		return returnValue;
	}
}

function ByteStream(bytes)
{
	this.bytes = bytes;  

	this.byteIndexCurrent = 0;
}
{
	// constants

	ByteStream.BitsPerByte = 8;
	ByteStream.BitsPerByteTimesTwo = ByteStream.BitsPerByte * 2;
	ByteStream.BitsPerByteTimesThree = ByteStream.BitsPerByte * 3;

	// instance methods

	ByteStream.prototype.hasMoreBytes = function()
	{
		return (this.byteIndexCurrent < this.bytes.length);
	}
	
	ByteStream.prototype.readBytes = function(numberOfBytesToRead)
	{
		var returnValue = [];

		for (var b = 0; b < numberOfBytesToRead; b++)
		{
			returnValue[b] = this.readByte();
		}

		return returnValue;
	}

	ByteStream.prototype.readByte = function()
	{
		var returnValue = this.bytes[this.byteIndexCurrent];

		this.byteIndexCurrent++;

		return returnValue;
	}

	ByteStream.prototype.readString = function(lengthOfString)
	{
		var returnValue = "";

		for (var i = 0; i < lengthOfString; i++)
		{
			var byte = this.readByte();

			if (byte != 0)
			{
				var byteAsChar = String.fromCharCode(byte);
				returnValue += byteAsChar;
			}
		}

		return returnValue;
	}

	ByteStream.prototype.writeBytes = function(bytesToWrite)
	{
		for (var b = 0; b < bytesToWrite.length; b++)
		{
			this.bytes.push(bytesToWrite[b]);
		}

		this.byteIndexCurrent = this.bytes.length;
	}

	ByteStream.prototype.writeByte = function(byteToWrite)
	{
		this.bytes.push(byteToWrite);

		this.byteIndexCurrent++;
	}

	ByteStream.prototype.writeString = function(stringToWrite, lengthPadded)
	{	
		for (var i = 0; i < stringToWrite.length; i++)
		{
			var charAsByte = stringToWrite.charCodeAt(i);
			this.writeByte(charAsByte);
		}
		
		var numberOfPaddingChars = lengthPadded - stringToWrite.length;
		for (var i = 0; i < numberOfPaddingChars; i++)
		{
			this.writeByte(0);
		}
	}
}

function Coords(x, y)
{
	this.x = x;
	this.y = y;
}
{
	Coords.prototype.overwriteWith = function(other)
	{
		this.x = other.x;
		this.y = other.y;
		return this;
	}

	Coords.prototype.toString = function()
	{
		return this.y + ":" + this.x;
	}
}

function Document(name, contents)
{
	this.name = name;
	this.contents = contents;

	this.cursorPos = new Coords(0, 0);
}
{
	// static methods

	Document.stringAndCharOffsetToCursorPos = function(text, cursorOffsetInChars)
	{
		var newline = "\n";
		var newlinesSoFar = 0;
		var offsetCurrent = null;

		while (true)
		{
			var offsetOfNewline = text.indexOf(newline, offsetCurrent);
			if (offsetOfNewline == -1 || offsetOfNewline >= cursorOffsetInChars)
			{
				break;
			}
			offsetCurrent = offsetOfNewline + 1;
			newlinesSoFar++;
		}

		var returnValue = new Coords
		(
			cursorOffsetInChars - offsetCurrent,
			newlinesSoFar + 1
		);

		return returnValue;
	}

	Document.stringAndCursorPosToCharOffset = function(text, cursorPos)
	{
		var newline = "\n";
		var newlinesSoFar = 0;
		var offsetCurrent = null;
	
		while (offsetCurrent < text.length && newlinesSoFar < cursorPos.y - 1)
		{
			var offsetOfNewline = text.indexOf(newline, offsetCurrent);
			offsetCurrent = offsetOfNewline + 1;
			newlinesSoFar++;
		}

		var returnValue = offsetCurrent + cursorPos.x;

		return returnValue;
	}

}

function FileHelper()
{
	// static class
}
{
    	FileHelper.loadFileAsBytes = function(fileToLoad, callback)
	{   
		var fileReader = new FileReader();
		fileReader.onload = function(fileLoadedEvent)
		{
			var fileLoadedAsBinaryString = 
				fileLoadedEvent.target.result;
			var fileLoadedAsBytes = 
				ByteHelper.stringUTF8ToBytes(fileLoadedAsBinaryString);
			callback(fileToLoad.name, fileLoadedAsBytes);
		}
 
		fileReader.readAsBinaryString(fileToLoad);
	}

	FileHelper.loadFileAsText = function(fileToLoad, callback)
	{
		var fileReader = new FileReader();
		fileReader.onload = function(fileLoadedEvent) 
		{
			var textFromFileLoaded = fileLoadedEvent.target.result;
			callback(fileToLoad.name, textFromFileLoaded);
		};
		fileReader.readAsText(fileToLoad);
	}
 
	FileHelper.saveBytesAsFile = function(bytesToWrite, fileNameToSaveAs)
	{
		var bytesToWriteAsArrayBuffer = new ArrayBuffer(bytesToWrite.length);
		var bytesToWriteAsUIntArray = new Uint8Array(bytesToWriteAsArrayBuffer);
		for (var i = 0; i < bytesToWrite.length; i++) 
		{
			bytesToWriteAsUIntArray[i] = bytesToWrite[i];
		}
 
		var bytesToWriteAsBlob = new Blob
		(
			[ bytesToWriteAsArrayBuffer ], 
			{ type:"application/type" }
		);
 
		var downloadLink = document.createElement("a");
		downloadLink.download = fileNameToSaveAs;
		downloadLink.href = window.URL.createObjectURL(bytesToWriteAsBlob);
		downloadLink.click();
	}

	FileHelper.saveTextAsFile = function(textToSave, fileNameToSaveAs)
	{
		var textToSaveAsBlob = new Blob([textToSave], {type:"text/plain"});
		var textToSaveAsURL = window.URL.createObjectURL(textToSaveAsBlob);

		var downloadLink = document.createElement("a");
		downloadLink.download = fileNameToSaveAs;
		downloadLink.href = textToSaveAsURL;
		downloadLink.click();
	}
}

function Globals()
{
	// do nothing
}
{
	Globals.Instance = new Globals();

	Globals.prototype.initialize = function(project)
	{
		this.session = new Session(project);
		this.domUpdate();
	}

	// dom 
	
	Globals.prototype.domUpdate = function()
	{
		this.session.domUpdate();	
	}
}

function Project(name, documents)
{
	this.name = name;
	this.documents = documents.addLookups("name");

	if (this.documents.length > 0)
	{
		this.documentIndexSelected = 0;
	}

	this.searchResults = [];
}
{
	Project.prototype.documentAdd = function(documentToAdd)
	{
		this.documents.push(documentToAdd);
		this.documents[documentToAdd.name] = documentToAdd;
		this.documentIndexSelected = this.documents.length - 1;
	}

	Project.prototype.documentNew = function()
	{
		var documentNew = new Document("Untitled.txt", "");
		this.documentAdd(documentNew);
	}

	Project.prototype.documentRemove = function(documentToRemove)
	{
		this.documents.remove(documentToRemove);
		delete this.documents[documentToRemove.name];
		if (this.documents.length == 0)
		{
			this.documentIndexSelected = null;
		}
	}

	Project.prototype.documentSelected = function()
	{
		return (this.documentIndexSelected == null ? null : this.documents[this.documentIndexSelected]);
	}

	Project.prototype.searchForText = function(textToSearchFor, matchCase)
	{
		this.searchResults.length = 0;

		if (matchCase == false)
		{
			textToSearchFor = textToSearchFor.toLowerCase();
		}

		for (var i = 0; i < this.documents.length; i++)
		{
			var documentToSearch = this.documents[i];
			var documentContents = documentToSearch.contents;

			if (matchCase == false)
			{
				documentContents = documentContents.toLowerCase();
			}

			var indexOfMatchInContents = -1;
			while (true)
			{
				indexOfMatchInContents = documentContents.indexOf
				(
					textToSearchFor,
					indexOfMatchInContents + 1
				);

				if (indexOfMatchInContents >= 0)
				{
					var matchPos = Document.stringAndCharOffsetToCursorPos
					(
						documentContents,
						indexOfMatchInContents
					);

					var newline = "\n";

					var lineWithMatchStart = documentContents.lastIndexOf(newline, indexOfMatchInContents);
					var lineWithMatchEnd = documentContents.indexOf(newline, indexOfMatchInContents);

					if (lineWithMatchStart == -1)
					{
						lineWithMatchStart = 0;
					}

					if (lineWithMatchEnd == -1)
					{
						lineWithMatchEnd = null;
					}

					var lineWithMatch = documentToSearch.contents.substring 
					(
						// Not the same as ".substr"!
						lineWithMatchStart, lineWithMatchEnd
					);

					var result = new SearchResult
					(
						documentToSearch.name, 
						matchPos,
						lineWithMatch
					)
					this.searchResults.push(result);
				}
				else
				{
					break;
				}
			}
		}
	}

	// dom

	Project.prototype.domUpdate = function()
	{
		var inputProjectName = 
			document.getElementById("inputProjectName");

		inputProjectName.value = this.name;

		var selectDocumentsInProject = 
			document.getElementById("selectDocumentsInProject");

		selectDocumentsInProject.options.length = 0;		

		for (var i = 0; i < this.documents.length; i++)
		{
			var _document = this.documents[i];
			var documentAsOption = document.createElement("option");
			documentAsOption.innerHTML = _document.name;
			selectDocumentsInProject.appendChild(documentAsOption);
		}

		var documentSelected = this.documentSelected();

		var inputDocumentSelectedName = 
			document.getElementById("inputDocumentSelectedName");
		var textareaDocumentSelectedContents = 
			document.getElementById("textareaDocumentSelectedContents");

		if (documentSelected == null)
		{
			inputDocumentSelectedName.value = "";
			textareaDocumentSelectedContents.value = "";
		}
		else
		{
			selectDocumentsInProject.selectedIndex = 
				this.documentIndexSelected;
			inputDocumentSelectedName.value = 
				documentSelected.name;
			textareaDocumentSelectedContents.value 
				= documentSelected.contents;
		}

		this.domUpdate_Cursor();

		this.domUpdate_Search();
	}

	Project.prototype.domUpdate_Cursor = function()
	{
		var inputCursorColumn = 
			document.getElementById("inputCursorColumn");
		var inputCursorRow = 
			document.getElementById("inputCursorRow");

		var documentSelected = this.documentSelected();

		if (documentSelected == null)
		{
			inputCursorRow.value = "";
			inputCursorColumn.value = "";
		}
		else
		{
			inputCursorRow.value = documentSelected.cursorPos.y;
			inputCursorColumn.value = documentSelected.cursorPos.x;
		}
	}

	Project.prototype.domUpdate_Cursor_Place = function()
	{	
		var documentSelected = this.documentSelected();

		if (documentSelected != null)
		{
			var cursorOffsetInChars = Document.stringAndCursorPosToCharOffset
			(
				documentSelected.contents,
				documentSelected.cursorPos
			);

			var textareaDocumentSelectedContents = 
				document.getElementById("textareaDocumentSelectedContents");

			textareaDocumentSelectedContents.selectionStart = cursorOffsetInChars; 
			textareaDocumentSelectedContents.selectionEnd = cursorOffsetInChars;
			textareaDocumentSelectedContents.focus();
		}
	}

	Project.prototype.domUpdate_Search = function()
	{
		var selectSearchResults = document.getElementById("selectSearchResults");
		selectSearchResults.innerHTML = "";

		for (var i = 0; i < this.searchResults.length; i++)
		{
			var searchResult = this.searchResults[i];
			var searchResultAsOption = document.createElement("option");
			searchResultAsOption.innerHTML = searchResult.toString();
			selectSearchResults.appendChild(searchResultAsOption);
		}
	}

	// tar

	Project.prototype.toTarFile = function()
	{
		var returnValue = TarFile.new();

		for (var i = 0; i < this.documents.length; i++)
		{
			var _document = this.documents[i];

			var documentContents = _document.contents;
			var documentContentsAsBytes = ByteHelper.stringUTF8ToBytes
			(
				documentContents
			);

			var documentAsTarFileEntry = TarFileEntry.fileNew
			(
				_document.name,
				documentContentsAsBytes	
			);
			returnValue.entries.push(documentAsTarFileEntry);
		}

		return returnValue;
	}
}

function SearchResult(documentName, posInDocument, lineContainingMatch)
{
	this.documentName = documentName;
	this.posInDocument = posInDocument;
	this.lineContainingMatch = lineContainingMatch;
}
{
	SearchResult.prototype.toString = function()
	{
		return this.documentName 
		+ " - " + this.posInDocument.toString() 
		+ " - " + this.lineContainingMatch;
	}
}


function Session(project)
{
	this.project = project;
}
{
	// dom

	Session.prototype.domUpdate = function()
	{
		this.project.domUpdate();
	}
}

function TarFile(fileName, entries)
{
	this.fileName = fileName;
	this.entries = entries;
}
{
	// constants

	TarFile.ChunkSize = 512;

	// static methods

	TarFile.fromBytes = function(fileName, bytes)
	{
		var reader = new ByteStream(bytes);

		var entries = [];

		var chunkSize = TarFile.ChunkSize;

		var numberOfConsecutiveZeroChunks = 0;

		while (reader.hasMoreBytes() == true)
		{
			var chunkAsBytes = reader.readBytes(chunkSize);

			var areAllBytesInChunkZeroes = true;

			for (var b = 0; b < chunkAsBytes.length; b++)
			{
				if (chunkAsBytes[b] != 0)
				{
					areAllBytesInChunkZeroes = false;
					break;
				}
			}

			if (areAllBytesInChunkZeroes == true)
			{
				numberOfConsecutiveZeroChunks++;

				if (numberOfConsecutiveZeroChunks == 2)
				{
					break;
				}
			}
			else
			{
				numberOfConsecutiveZeroChunks = 0;

				var entry = TarFileEntry.fromBytes(chunkAsBytes, reader);

				entries.push(entry);
			}
		}

		var returnValue = new TarFile
		(
			fileName,
			entries
		);

		return returnValue;
	}
	
	TarFile.new = function(fileName)
	{
		return new TarFile
		(
			fileName,
			[] // entries
		);
	}

	// instance methods
	
	TarFile.prototype.downloadAs = function(fileNameToSaveAs)
	{	
		FileHelper.saveBytesAsFile
		(
			this.toBytes(),
			fileNameToSaveAs
		)
	}	
	
	TarFile.prototype.entriesForDirectories = function()
	{
		var returnValues = [];
		
		for (var i = 0; i < this.entries.length; i++)
		{
			var entry = this.entries[i];
			if (entry.header.typeFlag.name == "Directory")
			{
				returnValues.push(entry);
			}
		}
		
		return returnValues;
	}
	
	TarFile.prototype.toBytes = function()
	{
		var fileAsBytes = [];		

		// hack - For easier debugging.
		var entriesAsByteArrays = [];
		
		for (var i = 0; i < this.entries.length; i++)
		{
			var entry = this.entries[i];
			var entryAsBytes = entry.toBytes();
			entriesAsByteArrays.push(entryAsBytes);
		}		
		
		for (var i = 0; i < entriesAsByteArrays.length; i++)
		{
			var entryAsBytes = entriesAsByteArrays[i];
			fileAsBytes = fileAsBytes.concat(entryAsBytes);
		}
		
		var chunkSize = TarFile.ChunkSize;
		
		var numberOfZeroChunksToWrite = 2;
		
		for (var i = 0; i < numberOfZeroChunksToWrite; i++)
		{
			for (var b = 0; b < chunkSize; b++)
			{
				fileAsBytes.push(0);
			}
		}

		return fileAsBytes;
	}
	
	// strings

	TarFile.prototype.toString = function()
	{
		var newline = "\n";

		var returnValue = "[TarFile]" + newline;

		for (var i = 0; i < this.entries.length; i++)
		{
			var entry = this.entries[i];
			var entryAsString = entry.toString();
			returnValue += entryAsString;
		}

		returnValue += "[/TarFile]" + newline;

		return returnValue;
	}
}

function TarFileEntry(header, dataAsBytes)
{
	this.header = header;
	this.dataAsBytes = dataAsBytes;
}
{
	// methods
	
	// static methods
	
	TarFileEntry.directoryNew = function(directoryName)
	{
		var header = new TarFileEntryHeader.directoryNew(directoryName);
		
		var entry = new TarFileEntry(header, []);
		
		return entry;
	}
	
	TarFileEntry.fileNew = function(fileName, fileContentsAsBytes)
	{
		var header = new TarFileEntryHeader.fileNew(fileName, fileContentsAsBytes);
		
		var entry = new TarFileEntry(header, fileContentsAsBytes);
		
		return entry;
	}
	
	TarFileEntry.fromBytes = function(chunkAsBytes, reader)
	{
		var chunkSize = TarFile.ChunkSize;
	
		var header = TarFileEntryHeader.fromBytes
		(
			chunkAsBytes
		);
	
		var sizeOfDataEntryInBytesUnpadded = header.fileSizeInBytes;	

		var numberOfChunksOccupiedByDataEntry = Math.ceil
		(
			sizeOfDataEntryInBytesUnpadded / chunkSize
		)
	
		var sizeOfDataEntryInBytesPadded = 
			numberOfChunksOccupiedByDataEntry
			* chunkSize;
	
		var dataAsBytes = reader.readBytes
		(
			sizeOfDataEntryInBytesPadded
		).slice
		(
			0, sizeOfDataEntryInBytesUnpadded
		);
	
		var entry = new TarFileEntry(header, dataAsBytes);
		
		return entry;
	}
	
	TarFileEntry.manyFromByteArrays = function
	(
		fileNamePrefix, fileNameSuffix, entriesAsByteArrays
	)
	{
		var returnValues = [];
		
		for (var i = 0; i < entriesAsByteArrays.length; i++)
		{
			var entryAsBytes = entriesAsByteArrays[i];
			var entry = TarFileEntry.fileNew
			(		
				fileNamePrefix + i + fileNameSuffix,
				entryAsBytes
			);
			
			returnValues.push(entry);
		}
		
		return returnValues;
	}
	
	// instance methods

	TarFileEntry.prototype.download = function(event)
	{
		FileHelper.saveBytesAsFile
		(
			this.dataAsBytes,
			this.header.fileName
		);
	}
	
	TarFileEntry.prototype.remove = function(event)
	{
		alert("Not yet implemented!"); // todo
	}
	
	TarFileEntry.prototype.toBytes = function()
	{
		var entryAsBytes = [];
	
		var chunkSize = TarFile.ChunkSize;
	
		var headerAsBytes = this.header.toBytes();
		entryAsBytes = entryAsBytes.concat(headerAsBytes);
		
		entryAsBytes = entryAsBytes.concat(this.dataAsBytes);

		var sizeOfDataEntryInBytesUnpadded = this.header.fileSizeInBytes;	

		var numberOfChunksOccupiedByDataEntry = Math.ceil
		(
			sizeOfDataEntryInBytesUnpadded / chunkSize
		)
	
		var sizeOfDataEntryInBytesPadded = 
			numberOfChunksOccupiedByDataEntry
			* chunkSize;
			
		var numberOfBytesOfPadding = 
			sizeOfDataEntryInBytesPadded - sizeOfDataEntryInBytesUnpadded;
	
		for (var i = 0; i < numberOfBytesOfPadding; i++)
		{
			entryAsBytes.push(0);
		}
		
		return entryAsBytes;
	}	
		
	// strings
	
	TarFileEntry.prototype.toString = function()
	{
		var newline = "\n";

		headerAsString = this.header.toString();

		var dataAsHexadecimalString = ByteHelper.bytesToStringHexadecimal
		(
			this.dataAsBytes
		);

		var returnValue = 
			"[TarFileEntry]" + newline
			+ headerAsString
			+ "[Data]"
			+ dataAsHexadecimalString
			+ "[/Data]" + newline
			+ "[/TarFileEntry]"
			+ newline;

		return returnValue
	}
	
}

function TarFileEntryHeader
(
	fileName,
	fileMode,
	userIDOfOwner,
	userIDOfGroup,
	fileSizeInBytes,
	timeModifiedInUnixFormat,
	checksum,
	typeFlag,
	nameOfLinkedFile,
	uStarIndicator,
	uStarVersion,
	userNameOfOwner,
	groupNameOfOwner,
	deviceNumberMajor,
	deviceNumberMinor,
	filenamePrefix
)
{
	this.fileName = fileName;
	this.fileMode = fileMode;
	this.userIDOfOwner = userIDOfOwner;
	this.userIDOfGroup = userIDOfGroup;
	this.fileSizeInBytes = fileSizeInBytes;
	this.timeModifiedInUnixFormat = timeModifiedInUnixFormat;
	this.checksum = checksum;
	this.typeFlag = typeFlag;
	this.nameOfLinkedFile = nameOfLinkedFile;
	this.uStarIndicator = uStarIndicator;
	this.uStarVersion = uStarVersion;
	this.userNameOfOwner = userNameOfOwner;
	this.groupNameOfOwner = groupNameOfOwner;
	this.deviceNumberMajor = deviceNumberMajor;
	this.deviceNumberMinor = deviceNumberMinor;
	this.filenamePrefix = filenamePrefix;
}
{
	TarFileEntryHeader.SizeInBytes = 500;

	// static methods
	
	TarFileEntryHeader.default = function()
	{
		var now = new Date();
		var unixEpoch = new Date(1970, 1, 1);
		var millisecondsSinceUnixEpoch = now - unixEpoch;
		var secondsSinceUnixEpoch = Math.floor
		(
			millisecondsSinceUnixEpoch / 1000
		);
		var secondsSinceUnixEpochAsStringOctal = 
			secondsSinceUnixEpoch.toString(8).padRight(12, " ");
		var timeModifiedInUnixFormat = []; 
		for (var i = 0; i < secondsSinceUnixEpochAsStringOctal.length; i++)
		{
			var digitAsASCIICode = 
				secondsSinceUnixEpochAsStringOctal.charCodeAt(i);
			timeModifiedInUnixFormat.push(digitAsASCIICode);
		}

		var returnValue = new TarFileEntryHeader
		(
			"".padRight(100, "\0"), // fileName
			"100777 \0", // fileMode
			"0 \0".padLeft(8, " "), // userIDOfOwner
			"0 \0".padLeft(8, " "), // userIDOfGroup
			0, // fileSizeInBytes
			timeModifiedInUnixFormat,
			0, // checksum
			TarFileTypeFlag.Instances.Normal,		
			"".padRight(100, "\0"), // nameOfLinkedFile,
			"".padRight(6, "\0"), // uStarIndicator,
			"".padRight(2, "\0"), // uStarVersion,
			"".padRight(32, "\0"), // userNameOfOwner,
			"".padRight(32, "\0"), // groupNameOfOwner,
			"".padRight(8, "\0"), // deviceNumberMajor,
			"".padRight(8, "\0"), // deviceNumberMinor,
			"".padRight(155, "\0") // filenamePrefix	
		);		
		
		return returnValue;
	}
	
	TarFileEntryHeader.directoryNew = function(directoryName)
	{
		var header = TarFileEntryHeader.default();
		header.fileName = directoryName;
		header.typeFlag = TarFileTypeFlag.Instances.Directory;
		header.fileSizeInBytes = 0;
		header.checksumCalculate();
		
		return header;
	}
	
	TarFileEntryHeader.fileNew = function(fileName, fileContentsAsBytes)
	{
		var header = TarFileEntryHeader.default();
		header.fileName = fileName;
		header.typeFlag = TarFileTypeFlag.Instances.Normal;
		header.fileSizeInBytes = fileContentsAsBytes.length;
		header.checksumCalculate();
		
		return header;
	}

	TarFileEntryHeader.fromBytes = function(bytes)
	{
		var reader = new ByteStream(bytes);

		var fileName = reader.readString(100).trim();
		var fileMode = reader.readString(8);
		var userIDOfOwner = reader.readString(8);
		var userIDOfGroup = reader.readString(8);
		var fileSizeInBytesAsStringOctal = reader.readString(12);
		var timeModifiedInUnixFormat = reader.readBytes(12);
		var checksumAsStringOctal = reader.readString(8);
		var typeFlagValue = reader.readString(1);
		var nameOfLinkedFile = reader.readString(100);
		var uStarIndicator = reader.readString(6);
		var uStarVersion = reader.readString(2);
		var userNameOfOwner = reader.readString(32);
		var groupNameOfOwner = reader.readString(32);
		var deviceNumberMajor = reader.readString(8);
		var deviceNumberMinor = reader.readString(8);
		var filenamePrefix = reader.readString(155);
		var reserved = reader.readBytes(12);

		var fileSizeInBytes = parseInt
		(
			fileSizeInBytesAsStringOctal.trim(), 8
		);
		
		var checksum = parseInt
		(
			checksumAsStringOctal, 8
		);		
		
		var typeFlags = TarFileTypeFlag.Instances._All;
		var typeFlagID = "_" + typeFlagValue;
		var typeFlag = typeFlags[typeFlagID];

		var returnValue = new TarFileEntryHeader
		(
			fileName,
			fileMode,
			userIDOfOwner,
			userIDOfGroup,
			fileSizeInBytes,
			timeModifiedInUnixFormat,
			checksum,
			typeFlag,
			nameOfLinkedFile,
			uStarIndicator,
			uStarVersion,
			userNameOfOwner,
			groupNameOfOwner,
			deviceNumberMajor,
			deviceNumberMinor,
			filenamePrefix
		);

		return returnValue;
	}

	// instance methods
	
	TarFileEntryHeader.prototype.checksumCalculate = function()
	{	
		var thisAsBytes = this.toBytes();
	
		// The checksum is the sum of all bytes in the header,
		// except we obviously can't include the checksum itself.
		// So it's assumed that all 8 of checksum's bytes are spaces (0x20=32).
		// So we need to set this manually.
						
		var offsetOfChecksumInBytes = 148;
		var numberOfBytesInChecksum = 8;
		var presumedValueOfEachChecksumByte = " ".charCodeAt(0);
		for (var i = 0; i < numberOfBytesInChecksum; i++)
		{
			var offsetOfByte = offsetOfChecksumInBytes + i;
			thisAsBytes[offsetOfByte] = presumedValueOfEachChecksumByte;
		}
		
		var checksumSoFar = 0;

		for (var i = 0; i < thisAsBytes.length; i++)
		{
			var byteToAdd = thisAsBytes[i];
			checksumSoFar += byteToAdd;
		}		

		this.checksum = checksumSoFar;
		
		return this.checksum;
	}
	
	TarFileEntryHeader.prototype.toBytes = function()
	{
		var headerAsBytes = [];
		var writer = new ByteStream(headerAsBytes);
		
		var fileSizeInBytesAsStringOctal = (this.fileSizeInBytes.toString(8) + " ").padLeft(12, " ")
		var checksumAsStringOctal = (this.checksum.toString(8) + " \0").padLeft(8, " ");

		writer.writeString(this.fileName, 100);
		writer.writeString(this.fileMode, 8);
		writer.writeString(this.userIDOfOwner, 8);
		writer.writeString(this.userIDOfGroup, 8);
		writer.writeString(fileSizeInBytesAsStringOctal, 12);
		writer.writeBytes(this.timeModifiedInUnixFormat);
		writer.writeString(checksumAsStringOctal, 8);
		writer.writeString(this.typeFlag.value, 1);		
		writer.writeString(this.nameOfLinkedFile, 100);
		writer.writeString(this.uStarIndicator, 6);
		writer.writeString(this.uStarVersion, 2);
		writer.writeString(this.userNameOfOwner, 32);
		writer.writeString(this.groupNameOfOwner, 32);
		writer.writeString(this.deviceNumberMajor, 8);
		writer.writeString(this.deviceNumberMinor, 8);
		writer.writeString(this.filenamePrefix, 155);
		writer.writeString("".padRight(12, "\0")); // reserved

		return headerAsBytes;
	}		
		
	// strings

	TarFileEntryHeader.prototype.toString = function()
	{		
		var newline = "\n";
	
		var returnValue = 
			"[TarFileEntryHeader "
			+ "fileName='" + this.fileName + "' "
			+ "typeFlag='" + (this.typeFlag == null ? "err" : this.typeFlag.name) + "' "
			+ "fileSizeInBytes='" + this.fileSizeInBytes + "' "
			+ "]"
			+ newline;

		return returnValue;
	}
}	

function TarFileTypeFlag(value, name)
{
	this.value = value;
	this.id = "_" + this.value;
	this.name = name;
}
{
	TarFileTypeFlag.Instances = new TarFileTypeFlag_Instances();

	function TarFileTypeFlag_Instances()
	{
		this.Normal 		= new TarFileTypeFlag("0", "Normal");
		this.HardLink 		= new TarFileTypeFlag("1", "Hard Link");
		this.SymbolicLink 	= new TarFileTypeFlag("2", "Symbolic Link");
		this.CharacterSpecial 	= new TarFileTypeFlag("3", "Character Special");
		this.BlockSpecial 	= new TarFileTypeFlag("4", "Block Special");
		this.Directory		= new TarFileTypeFlag("5", "Directory");
		this.FIFO		= new TarFileTypeFlag("6", "FIFO");
		this.ContiguousFile 	= new TarFileTypeFlag("7", "Contiguous File");

		// Additional types not implemented:
		// 'g' - global extended header with meta data (POSIX.1-2001)
		// 'x' - extended header with meta data for the next file in the archive (POSIX.1-2001)
		// 'A'–'Z' - Vendor specific extensions (POSIX.1-1988)
		// [other values] - reserved for future standardization

		this._All = 
		[
			this.Normal,
			this.HardLink,
			this.SymbolicLink,
			this.CharacterSpecial,
			this.BlockSpecial,
			this.Directory,
			this.FIFO,
			this.ContiguousFile,
		];

		for (var i = 0; i < this._All.length; i++)
		{
			var item = this._All[i];
			this._All[item.id] = item;
		}
	}
}
//run
main();




