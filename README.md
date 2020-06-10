# WebCrawling
[테스트 페이지 주소]
- http://localhost:8080

**[요구사항]**
- URL: 모든 문자 입력 가능
- 영서, 숫자만 출력
- 오름차순 정렬
    -   영어: AaBbCc...XxYyZz
    -   숫자: 0123456789
- 교차 출력
    - Source: AAAab, 01
    - Result: A0A1Aab
- 묶음 출력
    - Source: A0A1Aab, 묶음 단위: 3, 9 
    - Result:
        - 3: 몫 - A0A, 나머지 - 1Aab
        - 9: 몫 - , 나머지 - A0A1Aab 
