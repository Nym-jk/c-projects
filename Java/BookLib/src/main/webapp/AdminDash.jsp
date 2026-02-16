<%@ page language="java" contentType="text/html; charset=UTF-8"
    import="java.util.ArrayList, classFile.objClass, jakarta.servlet.http.HttpSession" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin-Dashboard</title>
</head>
<body>
<style>
    *{
        /*border:1px solid black;*/     
        font-family:'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif
    }
    body{
        height:100vh;
        background-color: burlywood;
        padding:10px;
    }
    #header{
        height:10vh;
        background-color: bisque;
        border-radius: 20px;
        box-sizing: border-box;
        position: relative;
        box-shadow: 2px 2px 5px 2px black;
    }
    #topic{
        display:inline-block;
        position: absolute; 
        margin:auto;
        left:5%;
        font-size:3em;
    }
    #form{
        position:absolute;
        right:10px;
        margin:20px 10px 20px; 
        display:flex;
        justify-content: space-around;
    }
    #section{
        background-color: coral;
        height:70vh;
        border-radius: 20px;
        display:flex;
        
    }
    #content{
        background-color: aquamarine;
        height:100%;
        width:80%;
        border-radius: 20px;
        overflow-y: auto;
        gap: 10px;
        justify-content:space-between;
        
    }
    #userdetail{
        height:30%;
        width:100%;
        background-color: beige;
        border-radius:20px;
        padding:20px;
        display:flex;
        gap:20%;
    }
  
    .each{
        flex:0 0 40%;
        margin:10px;
        background-color: #f1f1f1;
        font-size: 30px;
        text-align: center;
        border-radius: 10px;
        box-sizing:border-box;
        overflow:hidden;
        box-shadow: 2px 2px 5px 2px black;
        padding:5px;
    }
    #desc{
        margin:10px 10px 5px 10px;
    }
    span{
        width:100%;
        display:flex;
        flex-wrap:wrap;
    }
    #add{
        background-color: antiquewhite;
        height:100%;
        width:20vw;   
        border-radius: 20px;
        box-shadow: 2px 2px 5px 2px black;
    }
    #asidediv{
        margin:10px;
        text-align: center;
        color:saddlebrown;
    }
    a{
        text-decoration: none;
        color:rgb(41, 221, 152);
    }
    #anchor{
        width:100px;
        height:100px;
        top:50%;
        left:50%;
    }
    #image{
        max-width: 100%;
        max-height: 100%;
    }
    #button{
        width:auto;
        height:50px;
    }
    #rembut{
        width:100%;
        background-color: crimson;
        border:none;
        color:white;
    }
    #footer{
        background-color: chocolate;
        height:10vh;
        border-radius: 20px;
        box-shadow: 2px 2px 5px 2px black;
    }
</style>
    <header id="header" >
        <h3 id="topic">Books</h3>
        <form action="search?val=admin" id="form" method="get"> <input type="search" id="sea" name="bkname" placeholder="search.."><button type="submit">search</button> </form>        
    </header>
    <div id="userdetail">
        <div id="box1">
        <h2 id="he">User Detail</h2>
        <div id="details">
       
        <% 
        String uname,psw;
        HttpSession ses = request.getSession(false);
        uname = (String) ses.getAttribute("name"); 
        psw = (String) ses.getAttribute("pwd");
        %>
        <table id="tab">
            <form action="editdet" method="post">
            <tr><td><label>User Name : </label></td><td><input type="text" placeholder="<%=uname %>" name="username"></td></tr>
            <tr><td><label>Password &nbsp; : </label></td><td><input type="text" placeholder="<%=psw %>" name="userpassword"></td></tr>
            <tr><td></td><td><input type="submit" value="Edit"></td></tr>
            </form>
        </table>
        </div>
        </div>
        <div id="box2">
            <h2>Add books</h2>
            <div id="details">
        <table id="tab">
            <form action="savefile" method="post" enctype="multipart/form-data">
            <tr><td></td></tr>
            <tr><td><label>Upload book : </label></td><td><input type="file" name="file"></td></tr>
            <tr><td></td><td><input type="submit" value="Submit"></td></tr>
            </form>
        </table>
        </div>
        </div>
    </div>
    <section id="section">
        <article id="content">
        <%
       		 ArrayList <objClass> list = new ArrayList<>();
    	     String bookloc = "";
        %>
            <div id="pdfcont" style="Display:none; position:relative;">
            <button onclick="closepdf()" style="position:abolute; top:0;right:0;">X</button>
            <iframe src="<%=bookloc%>" width="100%" height="100%" id="pdfframe" style="background-color: aliceblue;"></iframe>
            </div>
            <span>
        <% 		
            boolean issearch =(Boolean) request.getAttribute("issearch");
            if(issearch){
            	list = (ArrayList<objClass>) request.getAttribute("searches");
            }else{
            	list = (ArrayList<objClass>) request.getAttribute("contents");
            }
			for(objClass obj : list)
			{
			bookloc= obj.getBloc();
		%>
				<div class="each"><img src="<%=obj.getCoverloc()%>" id="image"><a href="#" onclick="showpdf('<%=obj.getBloc()%>')" id="anchor" ><%=obj.getBname()%></a><div id="button"><form action="rembook" method="post"><input type="text" style="display:none" name="removebookname" value="<%=obj.getBname()%>" ><button type="submit" id="rembut">Remove</button></form></div></div>			
		<%	
			}
		%>
			</span>
        </article>
        <aside id="add">
            <div id="asidediv">
            <h3 class="mess">Back To</h3>
            <a href="toprofile?val=home" id="userbutton">Homepage</a>
            </div>
        </aside>
    </section>
    <footer id="footer"></footer>
<script>
    function showpdf(url){
        document.getElementById('pdfframe').src=url;
        document.getElementById('pdfcont').style.display="block";
    }
    function closepdf(){
        document.getElementById('pdfframe').src="";
        document.getElementById('pdfcont').style.display="none";
    }

</script>

</body>
</html>