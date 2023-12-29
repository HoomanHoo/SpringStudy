- PrintWriter.write() 만으로도 동적인 HTML 생성은 가능함
```
        for (Member member : members) {
            w.write(" <tr>");
            w.write(" <td>" + member.getId() + "</td>");
            w.write(" <td>" + member.getUsername() + "</td>");
            w.write(" <td>" + member.getAge() + "</td>");
            w.write(" </tr>");
```
- write() 메서드를 전부 타이핑 하는 것은 사람에 의한 실수를 유발할 확률이 높음
- 