import { Component, OnInit } from '@angular/core';
import { TaskI} from '../../model/task.interface';
import { TodoService } from '../../services/todo.service';
import { ActivatedRoute} from '@angular/router';
import { NavController, LoadingController } from '@ionic/angular';
import { emit } from 'process';

@Component({
  selector: 'app-todo-details',
  templateUrl: './todo-details.page.html',
  styleUrls: ['./todo-details.page.scss'],
})
export class TodoDetailsPage implements OnInit {
  todo: TaskI = {
    email: ''};

  uid= null;


  constructor(private route: ActivatedRoute, private nav: NavController, private todoService: TodoService,
     private loadingController: LoadingController) { }

  ngOnInit() {
    this.uid = this.route.snapshot.params['uid'];
    if (this.uid){
      this.loadTodo();
    }
  }
  async loadTodo(){
    const loading = await this.loadingController.create({
      message: 'Loading....'
    });
    await loading.present();

    this.todoService.getTodo(this.uid).subscribe(res => {
      loading.dismiss();;
      this.todo = res;
    });
  }

  async saveTodo() {
    const loading = await this.loadingController.create({
      message: 'Saving....'
    });
    await loading.present();
 
    if (this.uid) {
      this.todoService.updateTodo(this.todo, this.uid).then(() => {
        loading.dismiss();
        this.nav.navigateForward('/admin');
      });
    } else {
      this.todoService.addTodo(this.todo).then(() => {
        loading.dismiss();
        this.nav.navigateForward('/admin');
      });
    }
  }
  async onRemoveTodo(idTodo:string) {
    console.log(idTodo);
    this.todoService.removeTodo(idTodo);
  }

}
