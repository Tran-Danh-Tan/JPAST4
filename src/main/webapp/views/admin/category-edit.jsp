<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<form action="${pageContext.request.contextPath}/admin/category/update" method="post" enctype="multipart/form-data">
    <input type="text" id="categoryid" name="categoryid" value="${cate.categoryId}" hidden="hidden"><br>
    
    <label for="categoryname">Category name:</label><br>
    <input type="text" id="categoryname" name="categoryname" value="${cate.categoryname}"><br>

    <label for="images">Images:</label><br>
    <c:choose>
        <c:when test="${cate.images.substring(0, 5) != 'https'}">
            <c:url value="/image?fname=${cate.images}" var="imgUrl" />
        </c:when>
        <c:otherwise>
            <c:url value="${cate.images}" var="imgUrl" />
        </c:otherwise>
    </c:choose>
    <img id="imagePreview" height="150" width="200" src="${imgUrl}" alt="Preview"/>
    <input type="file" onchange="chooseFile(this)" id="images" name="images"><br>

    <label for="status">Status:</label><br>
    <input type="text" id="status" name="status" value="${cate.status}"><br>
    
    <br>
    <input type="submit" value="Submit">
</form>