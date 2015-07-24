<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="/include/taglib.jsp"%>
   <h2><span><a href="initSuccessStory.do">更多>></a></span>成功故事</h2>
      <div class="anli_main">
        <ul>
        <li>
          <div class="anli_ul">
           <s:iterator value="#request.storyList" var="bean">
          <div class="anli_li">
            <div class="anti_tx"> <a href="frontQuerySuccessStoryDetail.do?id=${bean.id}"><img src="${bean.imgPath}" width="62" height="62" /></a></div>
            <div class="anli_txt" >
              <h3><a href="frontQuerySuccessStoryDetail.do?id=${bean.id}" title="${bean.title}"><shove:sub value="title" size="30" /></a></h3>
              <p  ><shove:sub value="content" size="45" />...</p>
            </div>
          </div>
          </s:iterator>
          </div>
          </li>
        </ul>
      </div>


    
      
   
