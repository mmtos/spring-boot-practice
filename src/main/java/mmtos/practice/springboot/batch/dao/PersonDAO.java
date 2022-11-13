package mmtos.practice.springboot.batch.dao;

import java.util.List;
import mmtos.practice.springboot.batch.dto.Person;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDAO {
    @Autowired
    private SqlSession sqlSession;

    public List<Person> getAllPerson() {
        return sqlSession.selectList("Person.findAll");
    }
}
