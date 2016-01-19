<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" type="text/css" href="/resources/css/files.table.css">
<script src="/resources/scripts/files_table.js"></script>

<div>
  <table id="filesExplorer">
    <tr>
      <th id="Type">Type</th>
      <th id="Name">Name</th>
      <th id="Size">Size</th>
      <th id="Date">Creation date</th>
      <th></th>
      <th></th>
    </tr>
    <c:if test='${!currentPath.equals("")}'>
      <tr>
        <td class="type_image_cell"><img class="fileico" src="/resources/images/pictograms/directory_back.png"></td>
        <td class="filename_cell"><a href="${userRootDir}${backDir}/?sort=${sortBy}&reverse=${reverseOrder}"> .. </a></td>
        <td class="filesize_cell"></td>
        <td class="creation_date_cell"></td>
        <td class="type_image_cell"></td>
        <td class="type_image_cell"></td>
      </tr>
    </c:if>
    <c:forEach items="${files}" var="fileItem">
      <tr id="${fileItem.fileName}">
        <td class="type_image_cell"><img class="fileico" src="/resources/images/fileico/${fileItem.fileExtension}.png" onerror="unknownExtension(this);"></td>
        <td class="filename_cell"><a href="${userRootDir}${currentPath}/${fileItem.fileName}<c:if test="${fileItem.isDirectory}">/?sort=${sortBy}&reverse=${reverseOrder}</c:if>">${fileItem.fileName}</a></td>
        <td class="filesize_cell">${fileItem.fileSize}</td>
        <td class="creation_date_cell">${fileItem.creationDateString}</td>
        <td class="control_button_cell"><img class="control_button_img download_button" id="download_${fileItem.fileName}" src="/resources/images/pictograms/download.png" title="Download ${fileItem.fileName}"></td>
        <td class="control_button_cell"><img class="control_button_img delete_button" id="delete_${fileItem.fileName}" src="/resources/images/pictograms/delete.png" title="Delete ${fileItem.fileName}"></td>
      </tr>
    </c:forEach>
  </table>
</div>