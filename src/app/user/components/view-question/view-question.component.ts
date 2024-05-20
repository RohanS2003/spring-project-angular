import { QuestionService } from './../../user-services/question-service/question.service';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { StorageService } from '../../../auth-services/storage-service/storage.service';
import { AnswerService } from '../../user-services/answer-services/answer.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-view-question',
  templateUrl: './view-question.component.html',
  styleUrl: './view-question.component.scss'
})


export class ViewQuestionComponent {

  questionId: number=this.activatedRoute.snapshot.params["questionId"];
  question: any;
  validateForm!: FormGroup;
  selectedFile!: File | null;
  imagePreview!: string | ArrayBuffer | null;
  formData: FormData=new FormData();
  answers: any[]=[];
  displayButton: boolean=false;

  constructor(
    private questionService: QuestionService,
    private answerService: AnswerService,
    private activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private matSnackBar: MatSnackBar

  ) {}

  ngOnInit(){

    this.validateForm=this.fb.group({
      body: [null, Validators.required]
    })
    this.getQuestionById();
  }

  getQuestionById(){
    this.questionService.getQuestionById(this.questionId).subscribe(
      (res)=>{
        console.log(res);
        this.question=res.questionDTO;
        res.answerDtoList.forEach((element: any) => {
          if(element.file!=null)
          {
            element.convertedImg='data:image/jpeg;base64,'+element.file.data;
          }
          this.answers.push(element);
        });
        if(StorageService.getUserId()==this.question.userId)
          {
            this.displayButton=true;
          }
      }
    )
  }

  addAnswer(){
    console.log(this.validateForm.value);
    const data=this.validateForm.value;
    data.questionId=this.questionId;
    data.userId=StorageService.getUserId();
    console.log(data);

    if (this.selectedFile) {
      this.formData.append("multipartFile", this.selectedFile);
    }

    this.answerService.postAnswer(data).subscribe(
      (res)=>{
        this.answerService.postAnswerImage(this.formData, res.id).subscribe(
          (res)=>{
            console.log("Post Answer Image API ",res);
        }
    )
        console.log("Post Answer API response ",res);
        if(res.id!=null)
        {
          this.matSnackBar.open("Answer posted successfully.", "Close", {duration: 5000});
        }
        else{
          this.matSnackBar.open("Something went wrong.", "Close", {duration: 5000});
        }
      }
    )
  }

  addVote(voteType:string, voted: number){
    if(voted==1 || voted==-1)
      {
        this.matSnackBar.open("You already voted.", "Close",
          {
            duration: 5000,
            panelClass: 'error-snackbar'
          }
        )
      }
      else
      {
        const data={
        voteType: voteType,
        userId: StorageService.getUserId(),
        questionId: this.questionId
      }
      this.questionService.addVoteToQuestion(data).subscribe((res) =>{
        console.log(res);
        if(res.id!=null)
          {
            this.matSnackBar.open("Vote added successfully.", "Close", {duration: 5000});
            this.getQuestionById();
          }
          else
          {
            this.matSnackBar.open("Something went wrong.", "Close", {duration: 5000});
          }
      });
      }
  }

  approveAnswer(answerId: number){
    this.answerService.approveAnswer(answerId).subscribe((res)=>{
      console.log(res);
      if(res.id!=null)
          {
            this.matSnackBar.open("Answer Approved successfully.", "Close", {duration: 5000});
            this.getQuestionById();
          }
          else
          {
            this.matSnackBar.open("Something went wrong.", "Close", {duration: 5000});
          }
      });
  }

  onFileSelected(event: any){
    const files: FileList = event.target.files;
    if (files && files.length > 0) {
        this.selectedFile = files[0];
        this.previewImage();
    }
  }

  previewImage(){
    if (this.selectedFile) {
        const reader=new FileReader();
        reader.onload=()=>{
            this.imagePreview=reader.result;
        };
        reader.readAsDataURL(this.selectedFile);
    }
  }

}
