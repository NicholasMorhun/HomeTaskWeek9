<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" type="text/css" href="/resources/css/files.table.css">
<script src="/resources/scripts/files.table.js"></script>

<div>
  <table id="filesExplorer">
    <tr>
      <th>Type</th><th>Name</th><th>Size</th><th>Creation date</th><th></th><th></th>
    </tr>
    <c:if test='${!currentPath.equals("")}'>
      <tr>
        <td class="type_image_cell"><img class="fileico" src="/resources/images/pictograms/directory_back.png"></td>
        <td class="filename_cell"><a href="${userRootDir}${backDir}/"> .. </a></td>
        <td class="filesize_cell"></td>
        <td class="creation_date_cell"></td>
        <td class="type_image_cell"></td>
        <td class="type_image_cell"></td>
      </tr>
    </c:if>
    <c:forEach items="${files}" var="fileItem">
      <tr>
        <td class="type_image_cell"><img class="fileico" src="/resources/images/fileico/${fileItem.fileExtension}.png"></td>
        <td class="filename_cell"><a href="${userRootDir}${currentPath}/${fileItem.fileName}<c:if test="${fileItem.isDirectory}">/</c:if>">${fileItem.fileName}</a></td>
        <td class="filesize_cell">${fileItem.fileSize}</td>
        <td class="creation_date_cell">${fileItem.creationDate}</td>
        <td class="control_button_cell"><img class="control_button_img" id="download_${fileItem.fileName}" src="/resources/images/pictograms/download.png" title="Download ${fileItem.fileName}"></td>
        <td class="control_button_cell"><img class="control_button_img" id="delete_${fileItem.fileName}" src="/resources/images/pictograms/delete.png" title="Delete ${fileItem.fileName}"></td>
      </tr>
    </c:forEach>
  </table>
</div>