# DataSearch
Search service using Open API



## 블로그 검색
> `GET` /search/blog  
> `Host` http://localhost:8080

<br/>

＃ Request
|Name|Type|Description|Default|Required|
|---|:---:|---|---|:---:|
|query|String|검색어| |○|
|sortType|String|정렬 타입<br/>ACCURACY:정확도 순<br/>RECENCY:최신 순|ACCURACY|X|
|page|Integer|페이지 번호|1|X|
|size|Integer|한 페이지에 노출되는 문서 수|1|X|
|ApiPlatform|String|KAKAO_API<br/>NAVER_API|KAKAO_API|X|


Response Body


http://localhost:8080/search/blog?query=%EA%B9%80%EB%B0%A5&apiPlatform=KAKAO_API&page=1
{
"code": "200",
"message": "success",
"data": {
"content": [
{
"title": "애기<b>김밥</b> 싸서 야무지게 주말 점심 보냈어요 :-)",
"contents": "애기<b>김밥</b>을 싸는 꿀팁 레시피로 주말 점심을 이렇게 또 보냈습니다. 주말마다 뭐 먹일까.. 늘 고민한다면 집<b>김밥</b> 어떠신가요? 어른 재료준비하면서 +1 만큼만 더 준비하면 간단합니다~ 재료는 호불호가 있어 알아서 가감해주세요 :-) 나님은 아무거나 잘먹지만, 최대한 안짜고 안자극을 원해요. 남편은 시금치 극혐...",
"url": "http://lalanim.tistory.com/64",
"blogName": "라라님 블로그",
"postDateTime": "2023-02-28T13:48:05Z"
},
],
"pageable": {
"pageNumber": 1,
"pageSize": 10,
"totalPages": 512779,
"totalElements": 5127786
},
"apiPlatform": "KAKAO_API"
}
}

http://localhost:8080/search/blog/popular-search-terms
{
"code": "200",
"message": "success",
"data": [
{
"query": "김밥",
"queryCount": 2
}
]
}