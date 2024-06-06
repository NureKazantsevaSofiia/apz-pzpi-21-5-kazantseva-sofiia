import {Component} from '@angular/core';
import {AdminService} from "../../../_services/admin.service";
import {Router} from "@angular/router";
import {UtilService} from "../../../_services/util.service";
import {DatabaseHistoryDTO} from "../../../_models/response/database-history-dto";
import {LoginService} from "../../../_services/login.service";

@Component({
  selector: 'app-database',
  templateUrl: './database.component.html',
  styleUrl: './database.component.css'
})
export class DatabaseComponent {

  protected readonly LoginService = LoginService;
  protected readonly UtilService = UtilService;
  protected databaseHistory: DatabaseHistoryDTO[] | undefined;
  protected startOfCurrentWeek = UtilService.getStartOfCurrentWeek()
  protected endOfCurrentWeek = UtilService.getEndOfCurrentWeek()
  protected fileName = ""
  private file: File | undefined;

  constructor(private adminService: AdminService, private router: Router) {
  }

  ngOnInit() {
    this.getDatabaseHistory();
  }

  protected async generateWeekDatabaseReport() {
    try {
      await this.adminService.generateWeekDatabaseReport().then(res => {
          res.subscribe(
            response => {
              const file = new Blob([response], {type: "application/pdf"});
              const fileUrl = URL.createObjectURL(file);
              window.open(fileUrl);
            },
            error => {
              UtilService.displayError(error, this.router)
            }
          )
        }
      )
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }

  protected async generateMonthDatabaseReport() {
    try {
      await this.adminService.generateMonthDatabaseReport().then(res => {
          res.subscribe(
            response => {
              const file = new Blob([response], {type: "application/pdf"});
              const fileUrl = URL.createObjectURL(file);
              window.open(fileUrl);
            },
            error => {
              UtilService.displayError(error, this.router)
            }
          )
        }
      )
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }

  protected async exportDatabase() {
    try {
      await this.adminService.exportDatabase().then(res => {
          res.subscribe(
            response => {
              const file = new Blob([response], {type: "application/zip"});
              const fileUrl = URL.createObjectURL(file);
              window.open(fileUrl);
              location.reload();
            },
            error => {
              UtilService.displayError(error, this.router)
            }
          )
        }
      )
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }

  protected async importDatabase() {
    try {
      await this.adminService.importDatabase(this.file!);
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }

  protected onFileSelected(event: any) {
    const file: File = event.target.files[0];

    if (file) {
      this.file = file;
      this.fileName = file.name;
    }
  }

  private async getDatabaseHistory() {
    try {
      this.databaseHistory = await this.adminService.getWeekDatabaseHistory();
    } catch (error) {
      UtilService.displayError(error, this.router)
    }
  }
}
