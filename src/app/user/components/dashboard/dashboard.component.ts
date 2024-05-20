import { Component } from '@angular/core';
import { QuestionService } from '../../user-services/question-service/question.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {
  questions: any[]=[];
  pageNum: number=0;
  total: number=0;

  constructor(private service: QuestionService){}

  ngOnInit(){
    this.getAllQuestion();
  }

  getAllQuestion(){
    this.service.getAllQuestion(this.pageNum).subscribe((res)=>{
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
