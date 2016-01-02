<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div>
  <table>
    <tr>
      <th>Type</th><th>Name</th><th>Size</th><th>Creation date</th>
    </tr>
    <c:if test='${!currentPath.equals("")}'>
      <tr>
        <td><img src="/resources/images/pictograms/directory_back.png"></td>
        <td><a href="${userRootDir}${backDir}/"> .. </a></td>
        <td></td>
        <td></td>
      </tr>
    </c:if>
    <c:forEach items="${files}" var="fileItem">
      <tr>
        <td><img src="/resources/images/fileico/${fileItem.fileExtension}.png"></td>
        <td><a href="${userRootDir}${currentPath}/${fileItem.fileName}<c:if test="${fileItem.isDirectory}">/</c:if>">${fileItem.fileName}</a></td>
        <td>${fileItem.fileSize}</td>
        <td>${fileItem.creationDate}</td>
      </tr>
    </c:forEach>
  </table>
</div>