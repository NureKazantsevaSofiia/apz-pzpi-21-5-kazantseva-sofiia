export class PaginatedResponse<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
  numberOfElements: number;


  constructor(content: T[], totalPages: number, totalElements: number, size: number, number: number,
              numberOfElements: number) {
    this.content = content;
    this.totalPages = totalPages;
    this.totalElements = totalElements;
    this.size = size;
    this.number = number;
    this.numberOfElements = numberOfElements;
  }
}
