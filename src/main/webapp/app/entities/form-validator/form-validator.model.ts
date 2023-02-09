import dayjs from 'dayjs/esm';

export interface IFormValidator {
  id: number;
  type?: string | null;
  value?: string | null;
  formName?: string | null;
  fieldName?: string | null;
  companyId?: number | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
}

export type NewFormValidator = Omit<IFormValidator, 'id'> & { id: null };
