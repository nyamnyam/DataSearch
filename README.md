# DataSearch
Blog Search service using Open API  
 - Kakao Blog Search Open API (default)
 - Naver Blog Search Open API

<br/>

### jar 파일 다운로드 링크
> https://github.com/nyamnyam/DataSearch/raw/master/DataSearch.jar
<br/>

### 서버 실행
> $java -jar DataSearch.jar
<br/>

### 개발 환경
> openjdk version "16.0.2"
<br/>

### API 기능
1. 블로그 검색 기능
   1. 기본적으로 Kokao Blog 검색 API를 사용하며 장애 발생 시 Naver Blog 검색 API를 사용합니다.
   2. 정확도/최신 순 Sorting이 가능합니다. (Optional / default 정확도 순)
   3. 플랫폼을 지정하여 조회할 수 있습니다. (Optional / default Kakao API)
2. 인기 검색어 목록 조회 기능
   1. 가장 많이 검색된 키워드 순서대로 최대 10개 까지의 목록이 조회됩니다.
   2. 검색어 별 횟수도 함께 제공합니다.
<br/>

## API spec

### Default Response Body
```json
{
    "code": "200",
    "message": "success",
    "data": {}
}
```

<br/>

### 블로그 검색
> `GET` /search/blog  
> `Host` http://localhost:8080

#### Request  
|Name|Type|Description|Default|Required|
|---|:---:|---|---|:---:|
|query|String|검색어| |○|
|sortType|String|정렬 타입<br/>ACCURACY:정확도 순<br/>RECENCY:최신 순|ACCURACY|X|
|page|Integer|페이지 번호|1|X|
|size|Integer|한 페이지에 노출되는 문서 수|10|X|
|apiPlatform|String|플랫폼<br/>KAKAO_API<br/>NAVER_API|KAKAO_API|X|


#### Response Body
|Name|Type|Description|
|---|:---:|---|
|content|List||
|content / title|String|블로그 제목|
|content / contents|String|블로그 내용|
|content / url|String|블로그 url|
|content / blogName|String|블로그 이름|
|content / postDateTime|String|포스팅 날짜시간|
|pageable / pageNumber|Integer|페이지 번호|
|pageable / pageSize|Integer|한 페이지당 포스팅 수|
|pageable / totalPages|Integer|총 페이지 수|
|pageable / totalElements|Integer|총 포스팅 수|
|apiPlatform|String|플랫폼<br/>KAKAO_API<br/>NAVER_API|

#### Sample
```http://localhost:8080/search/blog?query=%EA%B9%80%EB%B0%A5```
```json
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
            }
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
```
<br/>

### 인기 검색어 목록
> `GET` /search/blog/popular-search-terms  
> `Host` http://localhost:8080


#### Response Body
|Name|Type|Description|
|---|:---:|---|
|query|String|검색어|
|queryCount|Long|조회수|


#### Sample
```http://localhost:8080/search/blog/popular-search-terms```
```json
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
```
