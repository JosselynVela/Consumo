import { Component, OnInit } from '@angular/core';
import { PeliService } from '../../services/peli.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-peli-detalle',
  templateUrl: './peli-detalle.page.html',
  styleUrls: ['./peli-detalle.page.scss'],
})
export class PeliDetallePage implements OnInit {
  content: any = null;

  constructor(private peliService: PeliService, private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    let id = this.activatedRoute.snapshot.paramMap.get('id');
    this.peliService.getDetails(id).subscribe(result => this.content = result);
  }
  

}
