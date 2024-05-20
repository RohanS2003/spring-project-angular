import { Component } from '@angular/core';
import { QuestionService } from '../../user-services/question-service/question.service';

@Component({
  selector: 'app-get-questions-by-userid',
  templateUrl: './get-questions-by-userid.component.html',
  styleUrl: './get-questions-by-userid.component.scss'
})
export class GetQuestionsByUseridComponent {
  questions: any[]=[];
  pageNum: number=0;
  total: number=0;

  constructor(private service: QuestionService){}

  ngOnInit(){
    this.getAllQuestion();
  }

  getAllQuestion(){
    this.service.getQuestionByUserId(this.pageNum).subscribe((res)=>{
      console.log(res);
      this.questions=res.qDtoList;
      this.total=res.totalPages*5;
    })
  }

  pageIndexChange(event: any){
    this.pageNum=event.pageIndex;
    this.getAllQuestion();
  }
}
