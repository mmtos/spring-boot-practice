package mmtos.practice.springboot.batch.dao;

import java.util.List;
import mmtos.practice.springboot.batch.dto.Hospital;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HospitalDAO {
    @Autowired
    private SqlSession sqlSession;

    public List<Hospital> getAllHospital() {
        return sqlSession.selectList("Hospital.selectAll");
    }
    public void mergeHospital(List<Hospital> hospitalList) {
        for(Hospital hospital : hospitalList){
            sqlSession.insert("Hospital.mergeHospital","hospital");
        }
    }
}
