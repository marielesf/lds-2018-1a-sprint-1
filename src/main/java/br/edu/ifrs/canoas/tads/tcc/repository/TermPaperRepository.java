package br.edu.ifrs.canoas.tads.tcc.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifrs.canoas.tads.tcc.domain.AcademicYear;
import br.edu.ifrs.canoas.tads.tcc.domain.TermPaper;

public interface TermPaperRepository extends JpaRepository<TermPaper, Long> {

    List<TermPaper> findByThemeContainingIgnoreCase(String theme);

    TermPaper findFirstByAuthorId(Long id, Sort sort);

    //@Query("FROM TermPaper t join Document d on (t.id = d.termPaper.id) join EvaluationBoard eb on eb.document.id = d.id join EvaluationBoardProfessors ebp on (ebp.evaluationBoard.id = eb.id)  and ebp.professors.id in (101)")
    //@Query("FROM TermPaper t join Document d on (t.id = d.termPaper.id) join EvaluationBoard eb on eb.document.id = d.id")// where eb.professors.id in (101)")
    //@Query("FROM TermPaper t join Document d on (t.id = d.termPaper.id) join EvaluationBoard eb on eb.document.id = d.id join eb.professors p where p.id in (101)")
    @Query(value = "SELECT term_paper.* FROM term_paper " +
            "join document on (term_paper.id = document.term_paper_id) " +
            "join evaluation_board on evaluation_board.document_id = document.id " +
            "join evaluation_board_professors on (evaluation_board_professors.evaluation_board_id = evaluation_board.id " +
            "and evaluation_board_professors.professors_id in (?1))  " +
            "where  term_paper.academic_year_id = (?2);", nativeQuery = true)
    List<TermPaper> getTermPaperForEvaluation(Long id, Long academicYear);

	TermPaper findFirstByAuthorIdAndAcademicYearOrderByIdDesc(Long authorId, AcademicYear academicYear);

}
