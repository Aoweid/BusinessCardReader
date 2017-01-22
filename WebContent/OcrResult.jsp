<%@ page language="java" import="storage.Database" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%
    String OCRPage = request.getParameter("page");
    int pageNumber = Integer.parseInt(OCRPage) - 1;
	/**TODO: get the coordinates of the image*/
	//Set the width and height of the image
	int width = 0;
	int height = 0;
	int x = -1,y = -1;
	//Get the local image path
	String imageLocalPath = Database.getStorage(pageNumber).getImageLocalPath();
	String imageRealPath = Database.getStorage(pageNumber).getImageRealPath();
	x = Integer.parseInt(request.getParameter("valX"));		
	y = Integer.parseInt(request.getParameter("valY"));		
	int index = Database.getOcrIndexPrecise(pageNumber, x, y);
	String ocrValue = "";
	String name = "";
	String address = "";
	String email = "";
	String phone = "";
	String fax = "";
	String wordID = "";
	String coordinates = "";
	name = Database.getStorage(pageNumber).getName();
	address = Database.getStorage(pageNumber).getAddress();
	phone = Database.getStorage(pageNumber).getPhone();
	fax = Database.getStorage(pageNumber).getFax();
	email = Database.getStorage(pageNumber).getEmail();
	if(index != -1) {
		ocrValue = Database.getStorage(pageNumber).getOCRValueList().get(index);
		wordID = Database.getStorage(pageNumber).getWordIDs().get(index);
		
	}
	if(x > 0 && y > 0) {
		coordinates = "(" + Integer.toString(x) + ", " + Integer.toString(y) + ")"; 
	}
%>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OCR Result Page</title>
<style type="text/css">
.tip {
width:200px;
border:2px solid #ddd;
padding:8px;
background:#f1f1f1;
color:#666;
}
#img{
 position: fixed;
 top: 200px;
 left: 500px;
 border: 3px solid #73AD21;
}
</style>
<script type="text/javascript"> 
function mousePos(e){
	var x,y;
	var e = e||window.event;
	return {
		x:Math.round(e.clientX+document.body.scrollLeft+document.documentElement.scrollLeft -500),		//Calibration!!!
		y:Math.round(e.clientY+document.body.scrollTop+document.documentElement.scrollTop -200) 		//Calibration!!!
	};
};
function test(e){
	window.location.href="OcrResult.jsp?page="+<%=OCRPage%>+"&valX="+mousePos(e).x+"&valY="+mousePos(e).y	
};
</script>
<link rel="stylesheet" href="styles/styles.css" type="text/css" media="screen" />
</head>
<body background="styles/background.jpg">
<body>
<div style="height: 10px"> </div>

      <h1 style = "text-align: center">Business Card Reader</h1>
      
      
      <div class="input-group" style="position: absolute; top: 200px; left: 50px">
      	<label for ="lastName">Name: <%=name %></label>
     	 
      </div>
      <div class="input-group" style="position: absolute; top: 250px; left: 50px">
      	<label for ="email">Email: <%=email %></label>

      </div>
      <div class="input-group" style="position: absolute; top: 300px; left: 50px">
      	<label for ="phoneNum">Phone Number: <%=phone %></label>
      	
      </div>
      <div class="input-group" style="position: absolute; top: 350px; left: 50px">
     	<label for ="address">Address: <%=address %></label>
      	
      </div>
      
      <div class="input-group" style="position: absolute; top: 400px; left: 50px">
     	<label for ="fax">Fax: <%=fax %></label>
      </div>
      
      <div style="position: absolute; top: 450px; left: 50px">
      <label for ="OCR Text Value">OCR Text Value: <%=ocrValue %></label>
      </div>
      
      <div style="position: absolute; top: 500px; left: 50px">
      <label for ="Coordinates">Coordinates: <%=coordinates %></label>
      </div>
      
      <div class="input-group" style="position: absolute; top: 550px; left: 50px">
      	</br><a href="display?page=0">Back</a></br>
		<a href="homepage">Back to HomePage</a></br>
      </div>
      	
	<div id="img" onclick="test(event)">
		<img src="<%=imageRealPath%>"/>
	</div>	
	
</body>
</html>