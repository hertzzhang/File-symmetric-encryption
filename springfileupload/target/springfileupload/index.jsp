<%--
  Created by IntelliJ IDEA.
  User: ZhangXianhao
  Date: 2020/3/5
  Time: 10:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>

    <script>
        function uploadFiles(){
            var uploadFile = new FormData($("#file")[0]);
            console.log(uploadFile);
            if("undefined" != typeof(uploadFile) && uploadFile != null && uploadFile != ""){
                $.ajax({
                    url:'springmvcfile/one',
                    type:'POST',
                    data:uploadFile,
                    async: false,
                    cache: false,
                    contentType: false, //不设置内容类型
                    processData: false, //不处理数据
                    success:function(data){
                       data = JSON.parse(data);
                       //获取表格对象
                        var ta = document.getElementById("ta");
                        //插入新的行
                        var tr = ta.insertRow(1);
                        var cell0 = tr.insertCell(0);
                        cell0.innerHTML=data.uuid;
                        var cell1 = tr.insertCell(1);
                        cell1.innerHTML=data.size;
                        var cell2 = tr.insertCell(2);
                        cell2.innerHTML=data.type;
                        var cell3 = tr.insertCell(3);
                        cell3.innerHTML=data.filename;
                        var cell4 = tr.insertCell(4);
                        cell4.innerHTML=data.time;
                        var cell5 = tr.insertCell(5);
                        cell5.innerHTML=data.address;


                    },
                    error:function(){
                        alert("上传失败！");
                    }
                })
            }else {
                alert("选择的文件无效！请重新选择");
            }
        }
    </script>

</head>
<body>
    <%--     <form    enctype="multipart/form-data">
                选择文件：<input type="file"name="fileload"><br>
                <input type="submit" value="传统方式上传" >
        </form>
--%>
         <br>
       <%--  <form action="springmvcfile/one"  method="post" enctype="multipart/form-data">
             选择文件：<input type="file"name="fileload"><br>
             <input type="button" value="上传文件" onclick="uploadFiles();"/>
         </form>
--%>
    <form method="post" id="file" action="" enctype="multipart/form-data">
        <h3>选择一个文件:</h3>
        <input id="excelFile" type="file" name="fileload" />
        <br/>
        <span id="id"></span>
        <table></table>

        <br/>
        <input type="button" value="上传" onclick="uploadFiles();"/>
    </form>
        <table border="1" id="ta">
            <tr>
                <td>uuid</td>
                <td>文件大小</td>
                <td>文件类型</td>
                <td>文件原始名</td>
                <td>上传时间</td>
                <td>文件地址</td>
            </tr>
        </table>
    <br>
    <br>
    <form action="springmvcfile/two" method="post">
        请输入文件地址:<input type="text" name="name"><br>


        <input type="submit"value="下载">
    </form>

</body>


</html>
