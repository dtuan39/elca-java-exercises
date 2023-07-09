import { Project } from './project.model';

export class ProjectPage {
  content: Project[];
  size: number;
  totalElements: number;
  totalPages: number;

  constructor(
    content: Project[],
    size: number,
    totalElements: number,
    totalPages: number
  ) {
    this.content = content;
    this.size = size;
    this.totalElements = totalElements;
    this.totalPages = totalPages;
  }
}
