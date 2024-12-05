** Rest api **

v1.0.1

Controller에서 데이터바인딩시 Entity객체를 절대 사용해선 안된다. 일단 Entity하나에 API를 위한 로직을
전부 담기가 힘들고 Entity가 변경될 시 API자체의 스펙이 변하기 때문이다.
데이터 바인딩시 AND 데이터 반환시 DTO객체를 사용해야한다.(Entity 직접노출 금지!)