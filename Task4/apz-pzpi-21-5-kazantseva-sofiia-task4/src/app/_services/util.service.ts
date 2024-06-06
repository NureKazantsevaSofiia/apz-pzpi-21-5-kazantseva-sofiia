import {Injectable} from '@angular/core';
import {Router} from "@angular/router";
import {LoginService} from "./login.service";
import getUserLocale from "get-user-locale";
import {DatePipe} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class UtilService {
  static readonly week = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
  static readonly year = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
  static readonly formatter = new DatePipe(getUserLocale(), Intl.DateTimeFormat().resolvedOptions().timeZone);

  static getTodayDate(): string {
    let formatter: DatePipe = new DatePipe(getUserLocale(), Intl.DateTimeFormat().resolvedOptions().timeZone);
    return formatter.transform(new Date(), "MMM d, y, HH:mm:ss") || "00-00-0000 00:00:00";
  }

  static getAmountOfDaysInMonth(): number[] {
    let today = new Date();
    let daysInMonth = new Date(today.getFullYear(), today.getMonth(), 0).getDate();
    let array = [];
    for (let i = 1; i <= daysInMonth; i++) {
      array[i - 1] = i;
    }
    return array;
  }

  static getYearPeriod(number: number | undefined): string {
    switch (number) {
      case 1:
        return 'January';
      case 2:
        return 'February';
      case 3:
        return 'March';
      case 4:
        return 'April';
      case 5:
        return 'May';
      case 6:
        return 'June';
      case 7:
        return 'July';
      case 8:
        return 'August';
      case 9:
        return 'September';
      case 10:
        return 'October';
      case 11:
        return 'November';
      case 12:
        return 'December';
      default:
        return '-';
    }
  }

  static getMonthPeriod(number: number | undefined): string {
    if (number == null || number == 0) {
      return "-";
    }
    let date = new Date();
    date.setDate(number);
    return this.formatDate(date) ?? "-";
  }

  static getWeekPeriod(number: number | undefined): string {
    switch (number) {
      case 1:
        return 'Monday';
      case 2:
        return 'Tuesday';
      case 3:
        return 'Wednesday';
      case 4:
        return 'Thursday';
      case 5:
        return 'Friday';
      case 6:
        return 'Saturday';
      case 7:
        return 'Sunday';
      default:
        return '-';
    }
  }

  static getColor(value: number): string {
    if (value <= 60) {
      return 'red';
    } else if (value >= 90) {
      return 'green';
    }
    return 'yellow';
  }

  static getSleepColor(value: number): string {
    if (value <= 30) {
      return 'green';
    } else if (value >= 70) {
      return 'red';
    }
    return 'yellow';
  }

  public static formatDate(date: Date | undefined): string | null {
    if (date == null) {
      return null;
    }
    return this.formatter.transform(date, "mediumDate");
  }

  public static formatTime(date: Date | undefined): string | null {
    if (date == null) {
      return null;
    }
    return this.formatter.transform(date, "HH:mm:ss");
  }

  public static formatDateTime(date: Date | undefined): string | null {
    if (date == null) {
      return null;
    }
    return this.formatter.transform(date, "dd.MM.YYYY HH:mm");
  }

  public static formatFullDateTime(date: Date | undefined): string | null {
    if (date == null) {
      return null;
    }
    return this.formatter.transform(date, "HH:mm:ss, dd.MM.YYYY");
  }

  public static formatLicenseDate(date: Date | undefined): string | null {
    if (date == null) {
      return null;
    }
    return this.formatter.transform(date, "MM/YY");
  }

  public static getStartOfCurrentWeek() {
    let date = new Date()
    date.setDate(date.getDate() - date.getDay() + 1)
    return this.formatDate(date) || '00.00.0000';
  }

  public static getEndOfCurrentWeek() {
    let date = new Date()
    date.setDate(date.getDate() - date.getDay() + 1)
    date.setDate(date.getDate() + 6)
    return this.formatDate(date) || '00.00.0000';
  }

  public static displayError(error: any, router: Router) {
    if (error.status == 401) {
      LoginService.logout(router)
    }
    if (error.error) {
      this.showError(error.error.errorMessage)
    } else if (error.message) {
      this.showError(error.message)
    } else {
      this.showError("Something went wrong!")
    }
  }

  public static displayAuthError(error: any) {
    if (error.error) {
      this.showError(error.error.errorMessage)
    } else if (error.message) {
      this.showError(error.message)
    } else {
      this.showError("Something went wrong!")
    }
  }

  public static showError(message: string) {
    let div = document.getElementById("alert");
    div!.textContent = message
    div!.style.display = "block";
    setTimeout(function () {
      div!.style.opacity = "0";
      div!.style.display = "none";
    }, 6000);
  }
}
